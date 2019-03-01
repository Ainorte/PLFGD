package unice.plfgd.tools;

import android.util.Log;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import unice.plfgd.BuildConfig;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.data.User;
import unice.plfgd.common.error.UnknownActionError;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Serializer;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tools.handler.ConnectHandler;
import unice.plfgd.tools.handler.DisconnectHandler;
import unice.plfgd.tools.handler.TimeoutHandler;

import java.net.URISyntaxException;

public class Connexion {

    private static Connexion INSTANCE;
    private static String SERVER_URL = "http://" + BuildConfig.SERVER_DOMAIN + ":" + BuildConfig.SERVER_PORT;

    public static Connexion getInstance() {
        if(INSTANCE == null) {
           INSTANCE = new Connexion();
        }
        return INSTANCE;
    }

    private Socket socket;

    private BasePresenter presenter;
    private User user;

    private Connexion() {
        this.user = user;
    }

    public boolean isConnected(){
        return socket != null;
    }

    public User getUser() {
        return user;
    }

    public BasePresenter getPresenter() {
        return presenter;
    }
    public void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

    public void openSocket(User user){
        this.user = user;

        try{
            socket = IO.socket(SERVER_URL);

            socket.on(Socket.EVENT_CONNECT_TIMEOUT, new TimeoutHandler(this));
            socket.on(Socket.EVENT_CONNECT_ERROR, new TimeoutHandler(this));
            socket.on(Socket.EVENT_CONNECT, new ConnectHandler(this));
            socket.on(Socket.EVENT_DISCONNECT, new DisconnectHandler(this));
            socket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length != 1) {
                        Log.e("OnMessage", "empty data");
                    }

                    Exchange packet = (Exchange) Serializer.BytesToObject((byte[]) args[0]);

                    assert packet != null;
                    Exception error = packet.getError();
                    if (error != null) {
                        Log.e("OnMessage", error.toString(), error);
                        return;
                    }

                    Exchange response = null;
                    if(packet.getAction().equals("draw")){
                        Draw draw = (Draw) packet.getPayload();
                        if (presenter instanceof DrawContract.Presenter){
                            DrawContract.Presenter drawPresenter = (DrawContract.Presenter) presenter;
                            drawPresenter.resultSwitch(draw);
                        }
                        //mainActivity.displayDrawing(draw);
                    }
                    else {
                        error = new UnknownActionError("No handler implemented for action " + packet.getAction());
                        Log.e("OnMessage","Bad Action" , error);
                        response = Exchange.with("message").error(error);
                    }

                    if (response != null) {
                        sendMessage(response);
                    }
                }
            });
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }

        socket.connect();
    }

    public void close(){
        socket.close();
    }

    public void Identify(){
        Exchange exchange = Exchange.with("ident").payload(user);
        sendMessage(exchange);
    }

    public void sendMessage(Exchange exchange){
        socket.emit("message", (Object) Serializer.ObjectToBytes(exchange));

        //Sending with Gson
        /*JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(exchange, Exchange.class));
            socket.emit("message", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    public void reset(){
        if(isConnected()){
            socket.close();
        }
    }

    public enum  ResetSocketMessage{
        CONNEXION_LOST,
        TIMEOUT
    }
}
