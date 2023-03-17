---
alias: Publish/Subscribe pattern
tags: 2023-03-17 SE publish subscribe pattern
source: 2023-03-17 Design pattern per architetture a microservizi.pdf
---

## descrizione:
permette l'utilizzo di un ==exchange (**X**)== che ==gestisce i messaggi==, aspettando la ==subscription del consumer(**C**)== con la quale potrà ==decidere quali messaggi farsi arrivare==
![](./img/pubsub.jpeg)

## run:
1. start di rabbitmq:
```
brew services start rabbitmq
```
2. run della classe:
```
$ cd project_path
$ java -cp .:path library:path library:path library src/folder/class.java
```