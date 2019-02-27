package unice.plfgd;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import unice.plfgd.common.data.Answer;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.data.User;
import unice.plfgd.common.forme.Point;
import unice.plfgd.common.net.Exchange;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
				Object[] tab = myCanvas.coords.toArray();
				Point[] points = Arrays.copyOf(tab, tab.length, Point[].class);
				txt.setText(Arrays.deepToString(tab));
				connexion.sendMessage(Exchange.with("draw").payload(new Draw(myCanvas.coords)));
			}
		});
	}

	public void displayDrawing(final List<Point> tab)
	{
		myCanvas.post(new Runnable() {
			@Override
			public void run() {
				setContentView(R.layout.activity_second);

				myCanvas = (MyCanvas) findViewById(R.id.My_Canvas);
				myCanvas.coords = tab;
				myCanvas.paint.setColor(Color.BLUE);
				myCanvas.setBackgroundColor(Color.GRAY);
				for(Point p : tab){
					if (p.isStart()) {
						myCanvas.path.moveTo((float) p.getX(), (float) p.getY());
					}
					else {
						myCanvas.path.lineTo((float) p.getX(), (float) p.getY());
					}
				}
				addListenerOnButton_save();
				addListenerOnButton_clear();
			}
		});
	}

}
