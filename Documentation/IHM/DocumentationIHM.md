!!! Nous ne traiterons pas les cas d'erreurs !!!

A = Ecran d'accueil
D = Dessiner une forme
MDF = Memoire de forme
RRT = Rectangle Rond Triangle

Boutons :
	- Valider : envoi le dessin/texte au serveur.
	- Reset :  permet d'effacer le dessin.
	- Rejouer : fait revenir au 1er ecran du jeu courant.
	- Retour : fait revenir à l'ecran principal.

Ecran d'accueil : Livraison 3
	- A-Pseudo : le joueur rentre son pseudo.
	- A-Accueil : le joueur choisit un jeu.

Dessiner une forme : Livraison 5
	- D-Dessin : Le joueur doit dessiner une forme (un rectangle ici).
	- D-Gagne : Le joueur a reussi à dessiner la forme.
	- D-Perdu : Le joueur a dessiner une autre forme que la forme demande.

Memoire de forme : Livraison 7
	- MDF-NbForme : Le joueur choisi le nombre de forme qu'il aura à memoriser (ici 2).
	- MDF-Forme1 : L'application affiche la 1ere forme à memoriser, avec un chrono de 10 secondes.
	- MDF-Forme2 : Aprés 10 secondes, l'application change de forme (ou non) et le chrono se remet à 10 secondes.
	- MDF-DF1 : Le joueur doit dessiner la 1ere forme.
	- MDF-DF2 : Le joueur doit dessiner la 2eme forme (s'il a valide la forme d'avant).
	- MDF-Echec : Le joueur a echouer a memoriser à la forme numero x, on lui affiche le nombre de forme qu'il a retenu, si 0 on lui affiche qu'il n'a rien retenu.
	- MDF-Victoire : Le joueur a valide toutes les formes.

Rectangle Rond Triangle : Livraison 6
	- RRT-Action : Le joueur dessine un carre (pierre), un rond (papier) ou un triangle (ciseau).
	- RRT-Gagne : Le joueur a gagne (ici : rectangle vs triangle = pierre vs ciseau = pierre gagne)
	- RRT-Perdu : Le joueur a perdu (ici : rectangle vs rond = pierre vs papier = papier gagne)



