package unice.plfgd;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
    Button button;
	Button button_edit;
	TextView text_edit;
	EditText text_input;

	Button button_main;
	Button button_clear;
	Button button_save;
	MyCanvas myCanvas;

	String text_bouton = "Envoi texte";
	String nom_joueur = "Joueur 1";
	String forme = "Carre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);


        User user = new User("Android Device");
        connexion = new Connexion("http://" + getBaseContext().getString(R.string.SERVER_DOMAIN) + ":" + getBaseContext().getString(R.string.SERVER_PORT), user, this);


		final TextView helloTextView = (TextView) findViewById(R.id.accueil_text);
		helloTextView.setText("Bienvenue " + nom_joueur);
		addListenerOnButton_des();
    }

	public void addListenerOnButton_des() {

		button = (Button) findViewById(R.id.but_des);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.dessin);
				//R.id.canvasTest.setContentView(myCanvas);
				// button_second n'existe pas encore
				// oblige de le cree il n'existe pas
				TextView nomjoueur = findViewById(R.id.des_text);
				nomjoueur.setText("Dessine un " + forme);
				addListenerOnButton_Val();
				addListenerOnButton_desreset();
			}

		});
	}

	public void addListenerOnButton_accueil() {

		button = (Button) findViewById(R.id.res_retour);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.accueil);
				final TextView helloTextView = (TextView) findViewById(R.id.accueil_text);
				helloTextView.setText("Bienvenue " + nom_joueur);
				addListenerOnButton_des();

			}

		});
	}

	public void addListenerOnButton_desreset() {

		button = (Button) findViewById(R.id.des_reset);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				myCanvas = (MyCanvas) findViewById(R.id.My_Canvas2);
				setContentView(R.layout.dessin);
				TextView nomjoueur = findViewById(R.id.des_text);
				nomjoueur.setText("Dessine un " + forme);
				addListenerOnButton_Val();
				addListenerOnButton_desreset();
			}

		});
	}

	public void addListenerOnButton_Val() {

		button = (Button) findViewById(R.id.des_button);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				myCanvas = (MyCanvas) findViewById(R.id.My_Canvas2);


				// test pour recupe le tableau des points et le mettre dans un TextView
				Object[] tab = myCanvas.coords.toArray();
				Point[] points = Arrays.copyOf(tab, tab.length, Point[].class);
				connexion.sendMessage(Exchange.with("draw").payload(new Draw(myCanvas.coords,myCanvas.getWidth(),myCanvas.getHeight())));

			}

		});
	}

	public void addListenerOnButton_rejouer() {

		button = (Button) findViewById(R.id.res_rejouer);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setContentView(R.layout.dessin);
				TextView nomjoueur = findViewById(R.id.des_text);
				nomjoueur.setText("Dessine un " + forme);
				addListenerOnButton_Val();
				addListenerOnButton_desreset();
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




	public void displayDrawing(final Draw d)
	{
		final List<Point> tab = d.getPoints();
		myCanvas.post(new Runnable() {
			@Override
			public void run() {
				setContentView(R.layout.dessin_resultat);
				myCanvas = (MyCanvas) findViewById(R.id.My_Canvas_res);
				myCanvas.b = 1;
				myCanvas.coords = tab;
				for(Point p : tab){
					if (p.isStart()) {
						//myCanvas.path.moveTo((float) (p.getX() * (myCanvas.getWidth() / d.getLar())), (float) (p.getY() * (myCanvas.getHeight() / d.getHaut())));
						myCanvas.path.moveTo((float) p.getX() , (float) p.getY());
					}
					else {
						//myCanvas.path.lineTo((float) (p.getX() * (myCanvas.getWidth() / d.getLar())), (float) (p.getY() * (myCanvas.getHeight() / d.getHaut())));
						myCanvas.path.lineTo((float) p.getX() , (float) p.getY());
					}
				}
				/*
				taille global fenetre :
				DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();
				int w = metrics.widthPixels;
				int h = metrics.heightPixels;
				*/
				TextView text = findViewById(R.id.resultat_text);
				text.setText("Bien jouer !");
				TextView coment = findViewById(R.id.res_coment);
				coment.setText("Tu sais dessiner !");
				//coment.setText(Arrays.deepToString(tab.toArray()));
				addListenerOnButton_accueil();
				addListenerOnButton_rejouer();
				myCanvas.b = 0;
			}
		});
	}

}
