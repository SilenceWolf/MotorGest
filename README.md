# MotorGest

Projet de POO avancée - Bachelor 3 Informatique - ESIEE-IT

## Groupe

- Kalotta Martin
- Matheo Isidoro
- Yohan Cana
- Louis Guillory

## Domaine

Gestion de flotte de vehicules. L'application MotorGest permet de gerer
les vehicules d'une entreprise de transport, les chauffeurs et les
missions qui leurs sont affectees. C'est une application desktop pensee
pour un usage interne par un responsable d'exploitation.

## Technologie

Interface Swing (option A du cahier des charges). On a choisi Swing parce
qu'on voulait une vraie application desktop avec des fenetres natives,
sans avoir a deployer un serveur Tomcat. Swing nous permet aussi de bien
mettre en avant le pattern modele/vue avec les TableModel personnalises.

L'interface a ete soignee : palette professionnelle (bleu nuit et accent
vert), boutons aux couleurs semantiques (vert pour ajouter, bleu pour
modifier, rouge pour supprimer), badges colores dans les tables pour
mettre en evidence les statuts, bandeau d'entete avec logo et barre
d'etat informative.

## Compilation et execution

Java 11 minimum requis. Depuis la racine du projet :

```
javac -d bin -sourcepath src $(find src -name "*.java")
java -cp bin view.Main
```

Sous Windows :

```
javac -d bin -sourcepath src src\model\*.java src\controller\*.java src\util\*.java src\view\*.java
java -cp bin view.Main
```

L'application charge automatiquement un jeu de donnees de demo au
demarrage. Le menu Fichier > Charger CSV permet de charger les fichiers
du dossier `resources/`.

## Fonctionnalites implementees

- CRUD complet sur vehicules, chauffeurs, missions
- Filtrage multicriteres (recherche texte + categorie + etat pour les
  vehicules, statut + type + ville de depart pour les missions)
- Tri dynamique sur toutes les colonnes des tables (clic sur l'entete)
- Affectation d'une mission a un couple vehicule+chauffeur avec
  verification du permis et de la disponibilite
- Cycle de vie des missions : planifiee -> en cours -> terminee
- File de priorite des missions par date de debut (PriorityQueue)
- Persistance CSV (chargement et sauvegarde)
- Raccourcis clavier Ctrl+O / Ctrl+S / Ctrl+Q
- Exceptions metier custom (VehiculeIndisponibleException,
  PermisInsuffisantException, CapaciteDepasseeException)
- Theme graphique soigne : badges colores, en-tetes de section,
  boutons aux couleurs semantiques

## Repartition des taches

- **Kalotta** : classes abstraites Vehicule et Mission, interface
  Identifiable, Registre generique borne, exceptions custom, README
  et donnees de demo
- **Matheo** : interfaces metier (Assignable, Maintenable, Facturable),
  classes concretes VehiculeLeger, VehiculeLourd, MissionCourte,
  dialogs Chauffeur et Mission, TableModel Chauffeur
- **Yohan** : controller GestionnaireFlotte, persistance CSV, classe
  Chauffeur, theme graphique (Theme + BadgeCellRenderer), interface
  Rafraichissable, actions menu charger/sauver, dialog affectation,
  panneau chauffeurs
- **Louis** : fenetre principale avec bandeau et barre d'etat, point
  d'entree Main, panneaux Vehicules et Missions, TableModels Vehicule
  et Mission, dialog Vehicule, enums StatutMission et TypePermis

## Structure du projet

```
MotorGest/
├── src/
│   ├── model/         classes metier, abstraites, interfaces, enums, exceptions
│   ├── view/          composants Swing (theme, panneaux, dialogs, TableModels)
│   ├── controller/    GestionnaireFlotte, PersistanceCSV
│   └── util/          Registre<T extends Identifiable>
├── resources/         CSV de donnees de demo
├── README.md
└── rapport_conception.pdf
```
