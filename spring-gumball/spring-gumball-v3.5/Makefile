all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

run-jar: build
	java -jar target/spring-gumball-3.5.jar


# Gumball Network

network-create:
	docker network create --driver bridge gumball

network-inspect:
	docker network inspect gumball

network-ls:
	docker network ls

network-prune:
	docker network prune

	
# RabbitMQ

rabbit-shell:
	docker exec -it rabbitmq bash 

rabbit:
	docker run --name rabbitmq \
	           --network gumball \
			   -p 8080:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management

rabbit-console:
	open http://localhost:8080

# Management Console: http://localhost:8080
# username and password of guest / guest:


# MySQL DB

mysql:
	docker run --platform=linux/amd64 -d --network gumball --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0

mysql-shell:
	docker exec -it mysql bash


# Jumpbox

jumpbox:
	docker run --platform=linux/amd64 --network gumball --name jumpbox -t -d ubuntu

jumpbox-shell:
	docker exec -it jumpbox bash 


# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-gumball .
	docker images

gumball-network:
	docker network create --driver bridge gumball

docker-run: docker-build
	docker run --platform=linux/amd64 --network gumball -e "MYSQL_HOST=mysql" --name spring-gumball -td -p 8080:8080 spring-gumball

docker-clean:
	docker stop spring-gumball
	docker rm spring-gumball
	docker rmi spring-gumball

docker-shell:
	docker exec -it spring-gumball bash

docker-push:
	docker login
	docker build --platform=linux/amd64 -t $(account)/spring-gumball:v3.5 .
	docker push $(account)/spring-gumball:v3.5

# Compose

compose-up:
	docker-compose up --scale gumball=2 -d

lb-up:
	docker-compose up -d lb

gumball-up:
	docker-compose up -d gumball

rabbit-up:
	docker-compose up -d rabbit

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost






