# Spring Payments (v1.2)


### Configure MySQL Connection (application.properties)

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/cmpe172
spring.datasource.username=admin
spring.datasource.password=welcome
```

### Run MySQL in Docker

```
docker run -d --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
docker exec -it mysql bash
mysql --password
```

### Create DB User

```
-- Creates the new database
mysql> create database cmpe172; 	

-- Creates the user						
mysql> create user 'admin'@'%' identified by 'welcome'; 	

-- Gives all privileges to the new user on the newly created database
mysql> grant all on cmpe172.* to 'admin'@'%'; 				

```

