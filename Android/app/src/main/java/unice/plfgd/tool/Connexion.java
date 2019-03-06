package unice.plfgd.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import unice.plfgd.BuildConfig;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.User;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.handler.ConnectHandler;
import unice.plfgd.tool.handler.DisconnectHandler;
import unice.plfgd.tool.handler.DrawHandler;
import unice.plfgd.tool.handler.TimeoutHandler;

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

    private Connexion() { }

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

			socket.on("draw", new DrawHandler(this));
        }

        catch (URISyntaxException e){
            e.printStackTrace();
        }

        socket.connect();
    }

    public void close(){
        socket.close();
    }

    public void Identify(){ sendMessage("ident",user); }

    public void sendMessage(String event, Packet payload){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());
        JSONObject object = mapper.convertValue(payload, JSONObject.class);

        socket.emit(event, object);
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
