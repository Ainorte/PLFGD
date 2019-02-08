package unice.plfgd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	Button button_edit;
	// text_bouton c'est pour l'exemple, suffit de la modif, c'est le texte affiche sur le bouton
	String text_bouton = "Envoi texte";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //On met le text de text_button sur le bouton
		button_edit = (Button) findViewById(R.id.button_edit);
		button_edit.setText(text_bouton);
		addListenerOnButton_edit();
    }

	public void addListenerOnButton_edit() {

		button_edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditText edit = (EditText)findViewById(R.id.text_input);
				String res = edit.getText().toString();
				TextView tv = (TextView)findViewById(R.id.text_edit);
				tv.setText(res);

			}

		});
	}
}
