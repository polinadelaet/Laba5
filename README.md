# Лабораторная работа 5-6

## Как подключить ***логгер***?
1.  Переходим по ссылке: [ApacheLog4jCore](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core). 
2.  Открываем самую свежую и вкусную версию
3.  Копируем строку в build.gradle 

>   **compile group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.13.3'**

![Альтернативный текст](https://sun1-22.userapi.com/dHxyPVdfE6LgYb-K1JY-woJ61Uw7HIjd7t025A/OasIetjGZRA.jpg "ну ЧТО ПОЛУНОЧНИКИ")

4.  При создании логгера импортируем **класс**, а не интерфейс
![Альтернативный текст](https://sun1-19.userapi.com/oCmL8xOYNNw_yDpP076AFAzYOzHsViyDUYcCgQ/kYzApTIPzB8.jpg "ПО КОФЕЙКУ И ТУЦ ТУЦ ПО КЛАВЕ")

5.  В скрипте, запускающем приложение прописываем:
>   **java -classpath "./log4j-core-2.13.3.jar" -jar Laba5-1.0-SNAPSHOT.jar**

https://howtodoinjava.com/log4j2/log4j-2-xml-configuration-example/
[ApacheLog4jCore](https://howtodoinjava.com/log4j2/log4j-2-xml-configuration-example/). 


[A](https://mkyong.com/logging/apache-log4j-2-tutorials/). 

[ApacheLog4jCore](https://www.scalyr.com/blog/maven-log4j2-project/). 

[ApacheLog4jCore](https://howtodoinjava.com/log4j2/log4j-2-xml-configuration-example/). 
