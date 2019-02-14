package unice.plfgd;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import unice.plfgd.common.data.Answer;
import unice.plfgd.common.data.User;
import unice.plfgd.common.net.Exchange;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Connexion connexion;
	Button button_edit;
	TextView text_edit;
	EditText text_input;

	Button button_main;
	Button button_clear;
	Button button_save;
	MyCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		myCanvas = (MyCanvas) findViewById(R.id.My_Canvas);

        User user = new User("Android Device");
        connexion = new Connexion("http://" + getBaseContext().getString(R.string.SERVER_DOMAIN) + ":" + getBaseContext().getString(R.string.SERVER_PORT), user, this);

        //On met le text de text_button sur le bouton

		text_edit= (TextView)findViewById(R.id.text_edit);
		text_edit.setText(R.string.wait);
        text_input = (EditText)findViewById(R.id.text_input);
		button_edit = (Button) findViewById(R.id.button_edit);
		button_edit.setText(R.string.send_response);
		addListenerOnButton_edit();
		addListenerOnButton_main();
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

	//Modif change de fenetre xml

	public void addListenerOnButton_main() {

		button_main = (Button) findViewById(R.id.button_main);

		button_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.activity_second);
				//R.id.canvasTest.setContentView(myCanvas);
				// button_second n'existe pas encore
				addListenerOnButton_clear();
				addListenerOnButton_save();
			}

		});
	}


	public void addListenerOnButton_clear() {

		button_clear = (Button) findViewById(R.id.clear_canvas);

		button_clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setContentView(R.layout.activity_second);
				addListenerOnButton_save();
				addListenerOnButton_clear();
			}

		});
	}

	public void addListenerOnButton_save() {

		button_save = (Button) findViewById(R.id.save_canvas);

		button_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				myCanvas = (MyCanvas) findViewById(R.id.My_Canvas);

				// test pour recupe le tableau des points et le mettre dans un TextView
				TextView txt = findViewById(R.id.canvasTest);
				txt.setText(Arrays.deepToString(myCanvas.coords.toArray()));

			}

		});
	}

	public void saveDrawing()
	{

		View view = findViewById(R.id.My_Canvas);
		Bitmap whatTheUserDrewBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

		//Bitmap whatTheUserDrewBitmap = view.getDrawingCache();
		// don't forget to clear it (see above) or you just get duplicates

		// almost always you will want to reduce res from the very high screen res
		whatTheUserDrewBitmap =
				ThumbnailUtils.extractThumbnail(whatTheUserDrewBitmap, 256, 256);
		// NOTE that's an incredibly useful trick for cropping/resizing squares
		// while handling all memory problems etc
		// http://stackoverflow.com/a/17733530/294884

        /*
        // you can now save the bitmap to a file, or display it in an ImageView:
        ImageView testArea = ...
        testArea.setImageBitmap( whatTheUserDrewBitmap );
        */

		// these days you often need a "byte array". for example,
		// to save to parse.com or other cloud services
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		whatTheUserDrewBitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
		byte[] yourByteArray;
		yourByteArray = baos.toByteArray();
	}

}
