# Projet persist-demo

## Aperçu
Ce projet est une évaluation de l'usage de @EntityListeners, une annotation JPA qui permet de suivre le cycle de vie des
Entities JPA.

Cette évaluation se doublera d'un démonstrateur et de tests afin de justifier l'évaluation.
Les sujets à traiter sont&nbsp;:

* [S1] l'intégration à Spring et notamment les Spring Bean
    * [S1.1] Est-ce qu'un EntityListener peut être un bean Spring ?
    * [S1.2] Est-ce qu'il est possible d'injecter un bean Spring dans un EntityListener ?

* [S2] la mise à jour de l'Entity via l'EntityListener fonctionne
    * [S2.1] lors de la création de l'Entity
    * [S2.2] lors de la mise à jour de l'Entity

* [S3] la mise à jour de l'Entity via l'EntityListener fonctionne avec le parent de l'Entity portant l'annotation
  @EntityListeners
    * [S3.1] lors de la création de l'Entity
    * [S3.2] lors de la mise à jour de l'Entity

* [S4] le support de tests unitaires
    * [S4.1] Est-ce possible de tester la création de l'Entity ?
    * [S4.2] Est-ce possible de tester la mise à jour de l'Entity ?

* [S5] le support complet en prod (ie pas en test)
    * [S5.1] Est-ce que la création de l'Entity fonctionne ?
    * [S5.2] Est-ce que la mise à jour de l'Entity fonctionne ?


## Cadre

persist-demo est un projet de type application Spring avec les supports de Web, JPA (Implementation Hibernate) et une
base de données SQL (H2).
Ce projet fournit donc la possibilité de proposer des services REST afin d'exécuter du code de production.
Il permet également de faire des tests unitaires via les librairies fournies par Spring.





# Module Cat

## Contenu

Package: com.example.persistdemo.cat

Fournit des End-Point REST dans la classe CatRest.

* GET /cats liste l'ensemble des chats enregistrés
* POST /cats créer un nouveau chat
  Exemple de body:
  { "id": 10,  "name": "Seth"  }
* GET /cats/{id} fournit le chat enregistré ayant pour identifiant {id}
* PUT /cats/{id} met à jour les propriétés du chat (uniquement le nom actuellement)
  Exemple de body:
  {   "name": "Seth"  }
* GET /cats/log/creation fournit les logs générés par l'EntityListener lors de la création d'une Entity
* GET /cats/log/update fournit les logs générés par l'EntityListener lors de la mise à jour d'une Entity

## Objectif
Faire un exemple simple en production pour vérifier que l'EntityListener est bien appelé lors de la création et de la 
mise à jour d'une Entity. 
Cela correspond à S2, S2.1, S2.2.


## Test Cat.1

### Objectif

Faire un exemple simple en production pour vérifier que l'EntityListener est bien appelé lors de la création d'une
Entity.
Cela correspond à S2.1.


### Création d'un chat

Commande:

```shell
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/cats --data '{ "id": 10, "name": "Sunny" }'
```

Réponse:

```json
{ "id": 10, "name": "Sunny" }
```

### Consultation des logs de création

Commande:

```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/cats/log/creation
```

Réponse:

```json
[ "10|Sunny" ]
```

### Consultation des logs des mise à jour

Commande:

```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/cats/log/update
```

Réponse:

```json
[]
```

### Conclusion du test

[S2.1] validé

## Test Cat.2

### Objectif

Faire un exemple simple en production pour vérifier que l'EntityListener est bien appelé lors de la mise à jour
d'une Entity.
Cela correspond à S2.2.


### Mise à jour d'un chat

Commande:

```shell
curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/cats/100 --data '{ "name": "Socket" }'
```

Réponse:

```json
{ "id": 100, "name": "Socket" }
```

### Consultation des logs de création

Commande:

```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/cats/log/creation
```

Réponse:

```json
[]
```

### Consultation des logs des mise à jour

Commande:

```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/cats/log/update
```

Réponse:

```json
[ "100|Socket" ]
```

### Conclusion du test

[S2.2] validé

## Conclusion du module
* [S2] :thumbsup: la mise à jour de l'Entity via l'EntityListener fonctionne 
  * [S2.1] :thumbsup: lors de la création de l'Entity
  * [S2.2] :thumbsup: lors de la mise à jour de l'Entity





# Module Dog
## Contenu
Package: com.example.persistdemo.parrot
Fournit des End-Point REST dans la classe DogRest.
* GET /dogs liste l'ensemble des chiens enregistrés
* POST /dogs créer un nouveau chien
Exemple de body:
{ "id": 10,  "name": "Seth"  }
* GET /dogs/{id} fournit le chien enregistré ayant pour identifiant {id}
* PUT /dogs/{id} met à jour les propriétés du chien (uniquement le nom actuellement)
Exemple de body:
```{ "name": "Seth" }```

* GET /dogs/log/creation fournit les logs générés par l'EntityListener lors de la création d'une Entity
* GET /dogs/log/update fournit les logs générés par l'EntityListener lors de la mise à jour d'une Entity

## Objectif
Faire un exemple avec héritage en production pour vérifier que l'EntityListener est bien appelé lors de la mise à
jour d'une Entity.
Cela correspond à S3, S3.1, S3.2.


## Test Dog.1
### Objectif
Faire un exemple d'héritage en production pour vérifier que l'EntityListener est bien appelé lors de la création d'une
Entity.
Cela correspond à S3.1.


### Création d'un chien
Commande:
```shell
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/dogs/ --data '{"id":10,"name":"Doggy"}'
```

Réponse:
```json
{ "id":10, "name":"Doggy" }
```


### Consultation des logs de création
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/creation
```

Réponse:
```json
["10|Doggy"]
```

### Consultation des logs des mise à jour
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/update
```

Réponse:
```json
[]
```

### Conclusion du test
[S3.1] validé


## Test Dog.2

### Objectif
Faire un exemple avec héritage en production pour vérifier que l'EntityListener est bien appelé lors de la mise à jour
d'une Entity.
Cela correspond à S3.2.


### Mise à jour d'un chien
Commande:
```shell
curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/dogs/100 --data '{ "name": "lucky" }'
```

Réponse:
```json
{"id":100,"name":"lucky"}
```

### Consultation des logs de création
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/creation
```

Réponse:
```json
[]
```

### Consultation des logs des mise à jour
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/update
```

Réponse:
```json
["100|lucky"]
```

### Conclusion du test
[S3.2] validé


## Conclusion du module
* [S3] :thumbsup: la mise à jour de l'Entity via l'EntityListener fonctionne avec le parent de l'Entity portant l'annotation
  @EntityListeners
  * [S3.1] :thumbsup: lors de la création de l'Entity
  * [S3.2] :thumbsup: lors de la mise à jour de l'Entity




# Module Mouse

## Contenu
Package: com.example.persistdemo.mouse

Fournit des End-Point REST dans la classe MouseRest&nbsp;:
* GET /mice liste l'ensemble des souris enregistrées
* POST /mice créer une nouvelle souris 
  Exemple de body:
  { "id": 10,  "name": "Daisy"  }
* GET /mice/{id} fournit la souris enregistrée ayant pour identifiant {id}
* PUT /mice/{id} met à jour les propriétés de la souris (uniquement le nom actuellement)
  Exemple de body:
  ```{ "name": "Daisy" }```
* GET /mice/logs fournit les logs générés par l'EntityListener:
  * lors de la création d'une Entity
  * la mise à jour d'une Entity
  * lors de l'instanciation de l'EntityListener
* GET /mice/listener fournit l'identifiant du ParentEntityListener injecté via Spring dans le contrôleur REST Spring. 



## Objectif
Faire un exemple avec héritage en production pour vérifier que l'EntityListener est bien appelé lors de la mise à
jour d'une Entity.
Cela correspond à&nbsp;:
* [S1] l'intégration à Spring et notamment les Spring Bean
  * [S1.1] Est-ce qu'un EntityListener peut être un bean Spring ?
  * [S1.2] Est-ce qu'il est possible d'injecter un bean Spring dans un EntityListener ?
* [S5] le support complet en prod (ie pas en test)
  * [S5.1] Est-ce que la création de l'Entity fonctionne ?
  * [S5.2] Est-ce que la mise à jour de l'Entity fonctionne ?


## Notes d'implémentation
La classe **Mouse** hérite de **ParentEntity**. **ParentEntity** contient 3 champs&nbsp;: **id**, **creationDate** et 
**updateDate**.
Ces deux derniers champs sont à remplir par l'EntityListener, à savoir **ParentEntityListener**.

Les valeurs des dates injectées dans ces champs sont issues du bean Spring **ClockProvider**.

Le bean Spring **ClockProvider** est injecté dans **ParentEntityListener** via Spring (ie utilisation de l'annotation 
**@Autowired**).

La vérification de l'injection via Spring de **ClockProvider** s'effectue en traçant la méthode d'injection et en 
traçant l'usage du **ClockProvider** pour remplir les champs **creationDate** et **updateDate**.

L'accès via Spring au bean **ParentEntityListener** ne fonctionne pas quand il n'y a pas d'annotation **@Component** ou
une de ses dérivées, **@Service** par exemple.

![Diagramme des classes JPA Entity, EntityListener et d'injection Spring ](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/gofannon-io/persist-demo/master/src/doc/mouse/ListenerEnv.puml)


## Test Mouse.1

### Objectif
Démontrer la faisabilité des points suivants&nbsp;:
* [S1.1] Est-ce qu'un EntityListener peut être un bean Spring ?
* [S1.2] Est-ce qu'il est possible d'injecter un bean Spring dans un EntityListener ?
* [S5.1] Est-ce que la création de l'Entity fonctionne ?

Pour évaluer le point [S1.1], l'annotation Spring *@Service* est ajoutée à la classe **ParentEntityListener**. 
Afin d'analyser le nombre d'instances créées, un champ **instanceId** avec une valeur unique est ajouté à la classe
**ParentEntityListener**.
La valeur de ce champ est ajoutée à chaque trace des opérations de **ParentEntityListener**.

Pour évaluer le point [S1.2], une instance du bean **ClockProvider** sera injectée dans l'instance de **EntityListener** 
afin de remplir les champs *creationDate* et *updateDate* de **ParentEntity**.

Pour évaluer le point [S5.1], un appel à un service REST de création de **Mouse** est effectué.
Cela est similaire aux tests précédents.


### Création d'une souris
Commande:
```shell
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/mice --data '{ "id":4, "name": "Igloo" }'
```

Réponse:
```json
{
  "id": 4,
  "creationDate": "2022-08-06T11:37:05.633+00:00",
  "updateDate": "2022-08-06T11:37:05.633+00:00",
  "name": "Igloo"
}
```

Premier constat, des valeurs sont bien injectées dans les champs *creationDate* et *updateDate* sont remplis avec la
même valeur. 
Cela signifie que l'injection Spring s'est bien passée.


### Consultation des logs
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/mice/logs
```

Réponse:
```json
{
  "listenerId": 2,
  "instanceTraces": [
    "Create instance number 1",
    "Inject clock provider to instance number 1",
    "Create instance number 2",
    "Inject clock provider to instance number 2"
  ],
  "onCreationTraces": [
    "Create mouse Igloo (id=4) from listener 1"
  ],
  "onUpdateTraces": []
}
```

Les logs contiennent des informations importantes&nbsp;:
* deux instances de **ParentEventListener** sont créées
  * "Create instance number 1"
  * "Create instance number 2"
* c'est l'instance 1 de **ParentEntityListener** qui met à jour les champs de l'Entity **Mouse**.
  * "Create mouse Igloo (id=4) from listener 1"
* c'est l'instance 2 de **ParentEntityListener** qui est injectée via Spring
  * "listenerId": 2


### Conclusion du test Mouse.1 
* Il est possible d'injecter des beans Spring dans l'EntityListener.
* Deux instances d'EntityListener sont créées. 
  * La première est créée par JPA/Hibernate et qui est utilisée pour la mise à jour des champs des entités.
    Elle n'est pas accessible via Spring. 
    Ainsi, il n'est pas possible de récupérer l'instance de l'EntityListener qui est utilisée par JPA pour gérer le 
    cycle de vie des **ParentEntity**.
  * La seconde est créée par Spring et est accessible via le registre Spring 



## Test Mouse.2

### Objectif
Démontrer la faisabilité des points suivants&nbsp;:
* [S5.2] Est-ce que la mise à jour de l'Entity fonctionne ?

Pour évaluer le point [S5.2], un appel à un service REST de mise à jour de **Mouse** est effectué.
Cela est similaire aux tests précédents.


### Mise à jour d'une souris
Commande:
```shell
curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/mice/100 --data '{ "name": "Mickey" }'
```

Réponse:
```json
{"id":100,"creationDate":"2022-08-09T16:51:06.189+00:00","updateDate":"2022-08-09T16:54:27.744+00:00","name":"Mickey"}
```

### Consultation des logs
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/mice/logs
```

Réponse:
```json
{
  "listenerId": 2,
  "instanceTraces": [
    "Create instance number 1",
    "Inject clock provider to instance number 1",
    "Create instance number 2",
    "Inject clock provider to instance number 2"
  ],
  "onCreationTraces": [],
  "onUpdateTraces": [
    "Update mouse Mickey (id=100) from listener 1"
  ]
}
```

Les logs contiennent des informations importantes qui confirment les informations générées lors de la création
* Tout comme pour la création, c'est l'instance 1 de ParentEntityListener qui met à jour les champs de l'Entity Mouse.
  * "Update mouse Mickey (id=100) from listener 1"



### Conclusion du test Mouse.2
Logiquement, la conclusion est identique à celle du test Mouse.1, à savoir que les mises à jour sont effectuées par le 
listener 1, celui qui est instancié par JPA/Hibernate, mais qui n'est pas accessible par Spring.


## Conclusion du module
L'instance de **ParentEntityListener**, utilisée pour la mise à jour des champs de **ParentEntity**, est créée par 
JPA/Hibernate, mais n'est pas accessible via Spring.
Lorsqu'une annotation **@Component** est ajoutée à **ParentEntityListener**, Spring crée une instance distincte de celle
créée par JPA/Hibernate. 
Elle n'est pas utilisée par JPA/Hibernate pour mettre à jour les entités **ParentEntity**. 

Néanmoins, il est possible d'injecter des beans Spring dans les instances de **ParentEntityListener**, qu'elles soient
créées par JPA/Hibernate ou Spring.

Le fait de ne pas pouvoir accéder à l'instance **ParentEntityListener** générée par JPA/Hibernate est un problème
surmontable en utilisant le design-pattern délégation. 
Il s'agira de déléguer les opérations, dans le **ParentEntityListener** instancié par JPA/Hibernate, à celles dans le 
**ParentEntityListener** instancié par Spring.


* [S1] :thumbsup: l'intégration à Spring et notamment les Spring Bean
  * [S1.1] :thumbsdown: Est-ce qu'un EntityListener peut être un bean Spring ? Non, pas directement, mais contournable.
  * [S1.2] :thumbsup: Est-ce qu'il est possible d'injecter un bean Spring dans un EntityListener ? Oui
* [S5] :thumbsup: le support complet en prod (ie pas en test)
  * [S5.1] :thumbsup: Est-ce que la création de l'Entity fonctionne ? Oui.
  * [S5.2] :thumbsup: Est-ce que la mise à jour de l'Entity fonctionne ? Oui.





# Module Parrot

## Contenu
Package: com.example.persistdemo.parrot 

Fournit des End-Point REST dans la classe ParrotRest&nbsp;:
* GET /parrots liste l'ensemble des perroquets enregistrés
* POST /parrots créer un nouveau perroquet
  Exemple de body:
  { "id": 10,  "name": "Coco"  }
* GET /parrots/{id} fournit le perroquet enregistré ayant pour identifiant {id}
* PUT /parrots/{id} met à jour les propriétés du perroquet (uniquement le nom actuellement)
  Exemple de body:
  ```{ "name": "Alita" }```
* GET /parrots/logs fournit les logs générés par l'EntityListener:
  * lors de la création d'une Entity
  * la mise à jour d'une Entity
  * lors de l'instanciation de l'EntityListener


## Objectif
Démontrer la faisabilité des points suivants&nbsp;:
* [S4] le support de tests unitaires
  * [S4.1] Est-ce possible de tester la création de l'Entity ?
  * [S4.2] Est-ce possible de tester la mise à jour de l'Entity ?


## Test Parrot.1
### Objectif
Démontrer la faisabilité des points suivants&nbsp;: 
* [S4] le support de tests unitaires
  * [S4.1] Est-ce possible de tester la création de l'Entity ?
  * [S4.2] Est-ce possible de tester la mise à jour de l'Entity ?


### Note d'implémentation des tests de la classe ParrotReporsitoryTest
Pour déclencher l'appel à **@PreUpdate**, il est nécessaire d'effectuer un flush de la requête.
De ce fait, il est nécessaire d'appeler **EntityManager#flush()**.

### Execution des tests de la classe ParrotRepositoryTest
Les tests sont "successful":
* L'appel de **ParrotRepository.save(Parrot)** pour une nouvelle entité appelle bien la méthode @PreCreate de l'instance 
  **ParrotEntityListener**.
* L'appel de **ParrotRepository.save(Parrot)** pour une entité préexistante appelle bien la méthode @PreUpdate de
  l'instance **ParrotEntityListener**.
* Il est possible d'effectuer au sein du même test une création et une mise à jour de la même Entity.


### Conclusion 
Les tests de création et de mise à jour des Entities fonctionnent correctement.

## Conclusion du module 
Avec un peu d'astuce, il est possible de tester la création et la mise à jour des Entities.

* [S4] :thumbsup: le support de tests unitaires
  * [S4.1] :thumbsup: Est-ce possible de tester la création de l'Entity ?
  * [S4.2] :thumbsup: Est-ce possible de tester la mise à jour de l'Entity ?





# Conclusion globale
Tous les points ont été satisfaits&nbsp;:
* [S1] :thumbsup: l'intégration à Spring et notamment les Spring Bean
  * [S1.1] :thumbsdown: Est-ce qu'un EntityListener peut être un bean Spring ? Non, mais cela est contournable
    facilement.
  * [S1.2] :thumbsup: Est-ce qu'il est possible d'injecter un bean Spring dans un EntityListener ?

* [S2] :thumbsup: la mise à jour de l'Entity via l'EntityListener fonctionne
  * [S2.1] :thumbsup: lors de la création de l'Entity
  * [S2.2] :thumbsup: lors de la mise à jour de l'Entity

* [S3] :thumbsup: la mise à jour de l'Entity via l'EntityListener fonctionne avec le parent de l'Entity portant  
  l'annotation @EntityListeners
  * [S3.1] :thumbsup: lors de la création de l'Entity
  * [S3.2] :thumbsup: lors de la mise à jour de l'Entity

* [S4] :thumbsup: le support de tests unitaires
  * [S4.1] :thumbsup: Est-ce possible de tester la création de l'Entity ?
  * [S4.2] :thumbsup: Est-ce possible de tester la mise à jour de l'Entity ?

* [S5] :thumbsup: le support complet en prod (ie pas en test)
  * [S5.1] :thumbsup: Est-ce que la création de l'Entity fonctionne ?
  * [S5.2] :thumbsup: Est-ce que la mise à jour de l'Entity fonctionne ?


L'usage des annotations **@PrePersist** et **OnUpdate** sur des classes parent d'Entities est validé.
