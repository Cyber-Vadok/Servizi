---
alias: Single queue pattern
tags: 2023-03-17 SE singlequeue pattern
source: 2023-03-17 Design pattern per architetture a microservizi.pdf
---

## descrizione:
permette di inviare messaggi in ==broadcast== tra ==producer (**P**)== e ==consumer (**C**)== tramite una ==singola coda==
![](./img/singlequeue.jpeg)

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