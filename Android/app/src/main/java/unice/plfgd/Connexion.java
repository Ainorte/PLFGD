package unice.plfgd;

import android.util.Log;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.data.Question;
import unice.plfgd.common.data.Result;
import unice.plfgd.common.data.User;
import unice.plfgd.common.error.UnknownActionError;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.common.net.Serializer;

import java.net.URISyntaxException;

public class Connexion {
    private Socket socket;
    private User user;
    private MainActivity mainActivity;

    public Connexion(String url, final User user, final MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.user = user;
        try{
            socket = IO.socket(url);

            socket.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    play();
                }
            });
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

                    if (packet.getAction().equals("question")) {
                        Question question = (Question)packet.getPayload();
                        Log.d("question", question.getQuestion());
                        mainActivity.changeText(question.getQuestion());

                    }
                    else if(packet.getAction().equals("result")){
                        Result result = (Result) packet.getPayload();
                        Log.d("result","Result : " + result.isRight());
                        mainActivity.changeText((result.isRight()) ? R.string.good_response : R.string.bad_response);

                    }
                    else if(packet.getAction().equals("draw")){
                        Draw draw = (Draw) packet.getPayload();
                        mainActivity.displayDrawing(draw.getPoints());
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

    public void play(){
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
}
