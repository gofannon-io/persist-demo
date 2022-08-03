# Aperçu

Ce projet est une évaluation de l'usage de @EntityListeners, une annotation JPA qui permet de suivre le cycle de vie des
Entities JPA.

Cette évaluation se doublera d'un démonstrateur et de tests afin de justifier l'évaluation.
Les sujets à traiter sont:

* [S1]    l'intégration à Spring et notamment les Spring Bean
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

* [S5] le support en prod (ie pas en test)
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

## Test Cat.1

### Objectif

Faire un exemple simple en production pour vérifier que l'EntityListener est bien appelé lors de la création d'une
Entity.

### Création d'un chat

Commande:

```shell
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/cats --data '{ "id": 10, "name": "Sunny" }'
```

Réponse:

```json
{
  "id": 10,
  "name": "Sunny"
}
```

### Consultation des logs de création

Commande:

```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/cats/log/creation
```

Réponse:

```json
[
  "10|Sunny"
]
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

### Conclusion

[S2.1] validé

## Test Cat.2

### Objectif

Faire un exemple simple en production pour vérifier que l'EntityListener est bien appelé lors de la mise à jour
d'une Entity.

### Mise à jour d'un chat

Commande:

```shell
curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/cats/100 --data '{ "name": "Socket" }'
```

Réponse:

```json
{
  "id": 100,
  "name": "Socket"
}
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
[
  "100|Socket"
]
```

### Conclusion

    [S2.2] validé


## Module Dog
### Contenu
Package: com.example.persistdemo.mouse
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

Objectif
Faire un exemple avec héritage en production pour vérifier que l'EntityListener est bien appelé lors de la mise à
jour d'une Entity.



### Test Dog.1
#### Objectif
Faire un exemple d'héritage en production pour vérifier que l'EntityListener est bien appelé lors de la création d'une
Entity.

#### Création d'un chien
Commande:
```shell
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/dogs/ --data '{"id":10,"name":"Doggy"}'
```

Réponse:
```json
{"id":10,"name":"Doggy"}
```


#### Consultation des logs de création
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/creation
```

Réponse:
```json
["10|Doggy"]
```

#### Consultation des logs des mise à jour
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/update
```

Réponse:
```json
[]
```

Conclusion
[S3.1] validé


### Test Dog.2

#### Objectif
Faire un exemple avec héritage en production pour vérifier que l'EntityListener est bien appelé lors de la mise à jour
d'une Entity.

#### Mise à jour d'un chien
Commande:
```shell
curl -X PUT -H 'Content-Type: application/json' -i http://localhost:8080/dogs/100 --data '{ "name": "lucky" }'
```

Réponse:
```json
{"id":100,"name":"lucky"}
```



#### Consultation des logs de création
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/creation
```

Réponse:
```json
[]
```

#### Consultation des logs des mise à jour
Commande:
```shell
curl -X GET -H 'Content-Type: application/json' -i http://localhost:8080/dogs/log/update
```

Réponse:
```json
["100|lucky"]
```

#### Conclusion
[S3.2] validé
