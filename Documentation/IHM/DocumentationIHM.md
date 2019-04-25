!!! Nous ne traiterons pas les cas d'erreurs !!!

A = Ecran d'accueil
D = Dessiner une forme
MDF = Memoire de forme
RRT = Rectangle Rond Triangle

Les modifications de l'it�rations 8 et 9 sont marqu�s � part

Boutons :
	- Valider : envoi le dessin/texte au serveur.
	- Reset :  permet d'effacer le dessin.
	- Rejouer : fait revenir au 1er ecran du jeu courant.
	- Retour : fait revenir � l'ecran principal.

Ecran d'accueil : Cr�ation � l'it�ration 3 et mise � jour jusqu'a l'it�ration 7
	- bienvenue : le joueur rentre son pseudo. (cr�ation it�ration 4)
		- It�ration 5 : le joueur peut jouer hors ligne en entrainement
	- accueil : le joueur choisit un jeu. (mise � jour jusqu'a l'it�ration 7)
	- connection : Permet au joueur de choisir son mode de jeu (connect�, hors-ligne, etc.)
	- properties : Isolation des informations serveur dans une fen�tre de param�tres

Dessiner une forme : Cr�ation � l'it�ration 3 et mise � jour jusqu'a l'it�ration 5
	- D-Dessin : Le joueur doit dessiner une forme (un rectangle ici). (cr�ation it�ration 3)
	- D-Gagne : Le joueur a reussi � dessiner la forme.
	- D-Perdu : Le joueur a dessiner une autre forme que la forme demande.

Memoire de forme : Cr�ation et livraison � l'it�ration finale
	- MDF-NbForme : Le joueur choisi le nombre de forme qu'il aura � memoriser (ici 2).
	- MDF-Forme1 : L'application affiche la 1ere forme � memoriser, avec un chrono de 10 secondes.
	- MDF-Forme2 : Apr�s 10 secondes, l'application change de forme (ou non) et le chrono se remet � 10 secondes.
	- MDF-DF1 : Le joueur doit dessiner la 1ere forme.
	- MDF-DF2 : Le joueur doit dessiner la 2eme forme (s'il a valide la forme d'avant).
	- MDF-Echec : Le joueur a echouer a memoriser � la forme numero x, on lui affiche le nombre de forme qu'il a retenu, si 0 on lui affiche qu'il n'a rien retenu.
	- MDF-Victoire : Le joueur a valide toutes les formes.

Rectangle Rond Triangle : Cr�ation et livraison � l'it�ration 7
	- RRT-Action : Le joueur dessine un carre (pierre), un rond (papier) ou un triangle (ciseau).
	- RRT-Gagne : Le joueur a gagne (ici : rectangle vs triangle = pierre vs ciseau = pierre gagne)
	- RRT-Perdu : Le joueur a perdu (ici : rectangle vs rond = pierre vs papier = papier gagne)

Mode entrainement (hors ligne uniquement) :
	- Le joueur dessine librement des formes



