package unice.plfgd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import unice.plfgd.common.data.Answer;
import unice.plfgd.common.data.User;
import unice.plfgd.common.net.Exchange;

public class MainActivity extends AppCompatActivity {

    private Connexion connexion;
	Button button_edit;
	TextView text_edit;
	EditText text_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = new User("Android Device");
        connexion = new Connexion("http://" + getBaseContext().getString(R.string.SERVER_DOMAIN) + ":" + getBaseContext().getString(R.string.SERVER_PORT), user, this);

        //On met le text de text_button sur le bouton

		text_edit= (TextView)findViewById(R.id.text_edit);
		text_edit.setText(R.string.wait);
        text_input = (EditText)findViewById(R.id.text_input);
		button_edit = (Button) findViewById(R.id.button_edit);
		button_edit.setText(R.string.send_response);
		addListenerOnButton_edit();
    }

	public void addListenerOnButton_edit() {

		button_edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
			    if(button_edit.getText().equals(getResources().getString(R.string.send_response))) {
                    text_edit.setText(R.string.wait);
                    button_edit.setText(R.string.replay);
                    String res = text_input.getText().toString();
                    connexion.sendMessage(Exchange.with("question").payload(new Answer(res)));
                }
			    else {
                    text_edit.setText(R.string.wait);
                    button_edit.setText(R.string.send_response);
                    connexion.play();
                    text_input.setText("");
                }
			}

		});
	}

	public void changeText(final String message){
		text_edit.post(new Runnable() {
			@Override
			public void run() {
				text_edit.setText(message);
			}
		});
	}
	public void changeText(final int message){
    	text_edit.post(new Runnable() {
			@Override
			public void run() {
				text_edit.setText(message);
			}
		});
	}
}
