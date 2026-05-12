# MotorGest

Projet de POO avancée - Bachelor 3 Informatique - ESIEE-IT

## Groupe

* Karlotta Martin
* Matheo Isidoro
* Yohan Cana
* Louis Guillory

## Domaine

Gestion de flotte de vehicules. L'application MotorGest permet de gerer
les vehicules d'une entreprise de transport, les chauffeurs, les missions
qui leurs sont affectees, les incidents qui peuvent survenir et fournit
des statistiques sur l'ensemble de la flotte. C'est une application
desktop pensee pour un usage interne par un responsable d'exploitation.

## Technologie

Interface Swing (option A du cahier des charges). On a choisi Swing parce
qu'on voulait une vraie application desktop avec des fenetres natives,
sans avoir a deployer un serveur Tomcat. Swing nous permet aussi de bien
mettre en avant le pattern modele/vue avec les TableModel personnalises.

L'interface a ete soignee avec un theme sombre type "voiture de sport" :
palette noir carbone, accents rouge profond et pourpre, boutons aux
couleurs semantiques (pourpre pour ajouter, rouge pour modifier, gris
sombre pour neutre, rouge corail pour supprimer), badges colores dans
les tables pour mettre en evidence les statuts (vert lime pour
disponible, jaune or pour planifie, etc.), bandeau d'entete avec logo
et barre d'etat informative. Le Look \& Feel Nimbus est reskinne au
demarrage via la classe Theme.

## Compilation et execution

Java 11 minimum requis. Depuis la racine du projet :

```
javac -d bin -sourcepath src $(find src -name "\*.java")
java -cp bin view.Main
```

Sous Windows :

```
javac -d bin -sourcepath src src\\model\\\*.java src\\controller\\\*.java src\\util\\\*.java src\\view\\\*.java
java -cp bin view.Main
```

L'application charge automatiquement un jeu de donnees de demo au
demarrage. Le menu Fichier > Charger CSV permet de charger les fichiers
du dossier `resources/`.

## Fonctionnalites implementees

* CRUD complet sur vehicules, chauffeurs, missions
* Filtrage multicriteres (recherche texte + categorie + etat pour les
vehicules, statut + type + ville de depart pour les missions)
* Tri dynamique sur toutes les colonnes des tables (clic sur l'entete)
* Recherche en temps reel sur le panneau vehicules (DocumentListener)
* Affectation d'une mission a un couple vehicule+chauffeur avec
verification du permis et de la disponibilite
* Cycle de vie des missions : planifiee -> en cours -> terminee
* File de priorite des missions par date de debut (PriorityQueue)
* Suivi des incidents avec gravite et cout
* Panneau disponibilites temps reel (vehicules et chauffeurs)
* Tableau de bord statistiques (repartitions, moyennes, top N) avec
graphiques en histogrammes dessines en Graphics2D
* Persistance CSV (chargement et sauvegarde)
* Export CSV des missions terminees
* Raccourcis clavier Ctrl+O / Ctrl+S / Ctrl+Q
* Exceptions metier custom (VehiculeIndisponibleException,
PermisInsuffisantException, CapaciteDepasseeException)
* Theme graphique sombre soigne : Look \& Feel Nimbus reskinne, badges
colores, en-tetes de section, boutons aux couleurs semantiques

## Repartition des taches

* **Karlotta** : classes abstraites Vehicule et Mission, interface
Identifiable, Registre generique borne, exceptions custom, classe
Incident, README et donnees de demo
* **Matheo** : interfaces metier (Assignable, Maintenable, Trackable,
Urgence, Facturable), classes concretes VehiculeLeger, VehiculeLourd,
VehiculeSpecial, MissionCourte, MissionLongue, dialogs Chauffeur et
Mission, TableModel Chauffeur, panneau incidents
* **Yohan** : controller GestionnaireFlotte, module Statistiques,
persistance CSV (avec export missions terminees), classe Chauffeur,
theme graphique (Theme + BadgeCellRenderer), interface
Rafraichissable, actions menu charger/sauver, dialog affectation,
panneau chauffeurs, panneau statistiques avec histogrammes
* **Louis** : fenetre principale avec bandeau et barre d'etat, point
d'entree Main, panneaux Vehicules et Missions, panneau Disponibilites,
TableModels Vehicule et Mission, dialog Vehicule, enums StatutMission
et TypePermis, recherche temps reel, raccourcis clavier

## Structure du projet

```
MotorGest/
├── src/
│   ├── model/         classes metier, abstraites, interfaces, enums, exceptions
│   ├── view/          composants Swing (theme, panneaux, dialogs, TableModels, histogramme)
│   ├── controller/    GestionnaireFlotte, Statistiques, PersistanceCSV
│   └── util/          Registre<T extends Identifiable>
├── resources/         CSV de donnees de demo
├── README.md
└── rapport\_conception.pdf
```
