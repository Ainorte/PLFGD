package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import unice.plfgd.common.Coup;
import unice.plfgd.common.Identification;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * attend une connexion, on envoie une question puis on attend une réponse, jusqu'à la découverte de la bonne réponse
 * le client s'identifie (som, niveau)
 */
public class Main {
	private SocketIOServer serveur;
	private final Object attenteConnexion = new Object();
	private int aTrouve = 42;
	private Identification leClient;
	private ArrayList<Coup> coups = new ArrayList<>();


	private Main(Configuration config) {
		// creation du serveur
		serveur = new SocketIOServer(config);

		// Objet de synchro

		System.out.println("préparation du listener");

		// on accept une connexion
		serveur.addConnectListener(socketIOClient -> {
			System.out.println("connexion de " + socketIOClient.getRemoteAddress());

			// on ne s'arrête plus ici
		});

		// réception d'une identification
		serveur.addEventListener("identification", Identification.class, (socketIOClient, identification, ackRequest) -> {
			System.out.println("Le client est " + identification.getNom());
			leClient = new Identification(identification.getNom(), identification.getLevel());

			// on enchaine sur une question
			poserUneQuestion(socketIOClient);
		});


		// on attend une réponse
		serveur.addEventListener("réponse", int.class, (socketIOClient, integer, ackRequest) -> {
			System.out.println("La réponse de  " + leClient.getNom() + " est " + integer);
			Coup coup = new Coup(integer, integer > aTrouve);
			if (integer == aTrouve) {
				System.out.println("le client a trouvé ! ");
				synchronized (attenteConnexion) {
					attenteConnexion.notify();
				}
			} else {
				coups.add(coup);
				System.out.println("le client doit encore cherché ");
				poserUneQuestion(socketIOClient, coup.isPlusGrand());
			}

		});


	}


	private void demarrer() {

		serveur.start();

		System.out.println("en attente de connexion");
		synchronized (attenteConnexion) {
			try {
				attenteConnexion.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("erreur dans l'attente");
			}
		}

		System.out.println("Une connexion est arrivée, on arrête");
		serveur.stop();

	}


	private void poserUneQuestion(SocketIOClient socketIOClient) {
		socketIOClient.sendEvent("question");
	}

	private void poserUneQuestion(SocketIOClient socketIOClient, boolean plusGrand) {
		socketIOClient.sendEvent("question", plusGrand, coups);
	}


	public static void main(String[] args) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

		Configuration config = new Configuration();
		config.setHostname("127.0.0.1");
		config.setPort(10101);


		Main serveur = new Main(config);
		serveur.demarrer();


		System.out.println("fin du main");

	}


}
