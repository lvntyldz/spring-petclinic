# uncompleted repo!


## Description
Simple petclinic springboot project.


## Run

```sh
$ mvn clean && mvn package
$ cd ./target/
$ java -jar petclinic-1.0-SNAPSHOT.jar
```

## Run as Development
```sh
$ mvn clean && mvn package
$ cd ./target/
$ java -jar -Dspring.profiles.active=dev  petclinic-1.0-SNAPSHOT.jar
```


## Web UI
To access actuator endpoints visit this url from local browser
(http://localhost:8080/actuator/health)

To access actuator environments visit this url from local browser
(http://localhost:8080/actuator/env)
