# Spring Gumball


## Version 3.5

* Switch to Spring Sessions JDBC
* Added RabbitMQ Support for Fulfilling Orders


## Spring Sessions JDBC Schema

* https://www.baeldung.com/spring-session-jdbc
* https://github.com/spring-projects/spring-session/blob/main/spring-session-jdbc/src/main/resources/org/springframework/session/jdbc/schema-mysql.sql


```
CREATE TABLE SPRING_SESSION (
  PRIMARY_ID CHAR(36) NOT NULL,
  SESSION_ID CHAR(36) NOT NULL,
  CREATION_TIME BIGINT NOT NULL,
  LAST_ACCESS_TIME BIGINT NOT NULL,
  MAX_INACTIVE_INTERVAL INT NOT NULL,
  EXPIRY_TIME BIGINT NOT NULL,
  PRINCIPAL_NAME VARCHAR(100),
  CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID CHAR(36) NOT NULL,
  ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
  ATTRIBUTE_BYTES BLOB NOT NULL,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
```




## Appendix A - Makefile (RabbitMQ Docker)

```
docker-clean:
  docker stop rabbitmq
  docker rm rabbitmq

network-create:
  docker network create --driver bridge springdemo

network-inspect:
  docker network inspect springdemo

network-ls:
  docker network ls

shell:
  docker exec -it rabbitmq bash 

rabbit:
  docker run --name rabbitmq \
             --network springdemo \
         -p 8080:15672 -p 4369:4369 -p 5672:5672 \
         -d rabbitmq:3-management
console:
  open http://localhost:8080

# Management Console: http://localhost:8080
# username and password of guest / guest:

```


## Appendix B - Deploying Single Node POD to GKE

* https://www.rabbitmq.com/networking.html#ports

* rabbitmq-pod.yaml

```
kubectl create -f rabbitmq-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: rabbitmq
  namespace: default
  labels:
    name: rabbitmq
spec:
  containers:
  - name: rabbitmq
    image: rabbitmq:3-management
    imagePullPolicy: Always
    ports:
    - containerPort: 15672
      name: console
      protocol: TCP
    - containerPort: 4369
      name: nodes
      protocol: TCP
    - containerPort: 5672
      name: client
      protocol: TCP
```

* rabbitmq-console.yaml

```
kubectl create -f rabbitmq-console.yaml
```

```
apiVersion: v1
kind: Service
metadata:
  name: rabbitmq-console
  namespace: default
spec:
  type: LoadBalancer
  ports:
  - port: 80 
    targetPort: 15672
    protocol: TCP
  selector:
    name: rabbitmq
```

* rabbitmq-service.yaml

```
kubectl create -f rabbitmq-service.yaml
```

```
apiVersion: v1
kind: Service
metadata:
  name: rabbitmq-service
  namespace: default
spec:
  type: ClusterIP
  ports:
  - port: 5672 
    targetPort: 5672
    protocol: TCP
  selector:
    name: rabbitmq
```




