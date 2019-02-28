!!! Nous ne traiterons pas les cas d'erreurs !!!

A = Ecran d'accueil
D = Dessiner une forme
MDF = Memoire de forme
RRT = Rectangle Rond Triangle

Les modifications de l'itérations 8 et 9 sont marqués à part

Boutons :
	- Valider : envoi le dessin/texte au serveur.
	- Reset :  permet d'effacer le dessin.
	- Rejouer : fait revenir au 1er ecran du jeu courant.
	- Retour : fait revenir à l'ecran principal.

Ecran d'accueil : Création à l'itération 3 et mise à jour jusqu'a l'itération 7
	- A-Pseudo : le joueur rentre son pseudo. (création itération 4)
	- A-Accueil : le joueur choisit un jeu. (mise à jour jusqu'a l'itération 7)

Dessiner une forme : Création à l'itération 3 et mise à jour jusqu'a l'itération 5
	- D-Dessin : Le joueur doit dessiner une forme (un rectangle ici). (création itération 3)
	- D-Gagne : Le joueur a reussi à dessiner la forme.
	- D-Perdu : Le joueur a dessiner une autre forme que la forme demande.

Memoire de forme : Création et livraison à l'itération 7
	- MDF-NbForme : Le joueur choisi le nombre de forme qu'il aura à memoriser (ici 2).
	- MDF-Forme1 : L'application affiche la 1ere forme à memoriser, avec un chrono de 10 secondes.
	- MDF-Forme2 : Aprés 10 secondes, l'application change de forme (ou non) et le chrono se remet à 10 secondes.
	- MDF-DF1 : Le joueur doit dessiner la 1ere forme.
	- MDF-DF2 : Le joueur doit dessiner la 2eme forme (s'il a valide la forme d'avant).
	- MDF-Echec : Le joueur a echouer a memoriser à la forme numero x, on lui affiche le nombre de forme qu'il a retenu, si 0 on lui affiche qu'il n'a rien retenu.
	- MDF-Victoire : Le joueur a valide toutes les formes.

Rectangle Rond Triangle : Création et livraison à l'itération 6
	- RRT-Action : Le joueur dessine un carre (pierre), un rond (papier) ou un triangle (ciseau).
	- RRT-Gagne : Le joueur a gagne (ici : rectangle vs triangle = pierre vs ciseau = pierre gagne)
	- RRT-Perdu : Le joueur a perdu (ici : rectangle vs rond = pierre vs papier = papier gagne)

Itération 8 :
	- Ajout de nouvel forme pouvant être reconnut (pentagone....)
	- Mise à jour de Dessiner une forme, Memoire de forme en conséquence.

Itération 9 :
	- A-iter9 : Mise en place d'un rectangle rond triangle en ligne (ajout d'un bouton à l'écran d'accueil)
	- RRT-Multi : On choisit un joueur avec qui jouez.
	La façon de jouer sera la même (voir Rectangle Rond Triangle)




