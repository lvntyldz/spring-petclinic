# uncompleted repo!

## Description
Simple petclinic springboot project.

## Run

```sh
$ mvn clean && mvn package
$ cd ./target/
$ java -jar petclinic-1.0-SNAPSHOT.jar
```

## Web UI
To access actuator endpoints visit this url from local browser
<br /> http://localhost:8080/actuator/health

To access actuator environments visit this url from local browser
<br /> http://localhost:8080/actuator/env

To access owner's Rest APIs  visit this url from local browser
<br /> http://localhost:8080/rest/owners
<br /> http://localhost:8080/rest/owner?ln=YILDIZ
<br /> http://localhost:8080/rest/owner/3

Dynamic Content type
<br /> default(xml) :  http://localhost:8080/rest/owner/3
<br /> to json :  http://localhost:8080/rest/owner/3.json

Databse Console
<br /> h2-console :  http://localhost:8080/h2-console

___
___


## Run as Development(Active Spring Profile is Development)
```sh
$ mvn clean && mvn package
$ cd ./target/
$ java -jar -Dspring.profiles.active=dev  petclinic-1.0-SNAPSHOT.jar
```

## Web UI(Development)
To access actuator endpoints visit this url from local browser
<br /> http://localhost:8081/petclinic/actuator/health

To access actuator environments visit this url from local browser
<br /> http://localhost:8081/petclinic/actuator/env

To access owner's Rest APIs  visit this url from local browser
<br /> http://localhost:8081/petclinic/rest/owners
<br /> http://localhost:8081/petclinic/rest/owner?ln=YILDIZ
<br /> http://localhost:8081/petclinic/rest/owner/3

Dynamic Content type
<br /> default(xml) :  http://localhost:8081/petclinic/rest/owner/3
<br /> to json :  http://localhost:8081/petclinic/rest/owner/3.json

Databse Console
<br /> h2-console :  http://localhost:8081/petclinic/h2-console
