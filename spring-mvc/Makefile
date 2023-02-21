clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

run-jar: build
	java -jar target/spring-mvc-1.0.jar

# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-mvc .
	docker images

docker-run: docker-build
	docker run --name spring-mvc --platform=linux/amd64 -td -p 90:8080 spring-mvc
	docker ps

up:
	docker-compose up --scale springmvc=2 -d

down:
	docker-compose down