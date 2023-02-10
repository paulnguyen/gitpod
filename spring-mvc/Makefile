clean:
	mvn clean

compile:
	mvn compile

run:
	mvn spring-boot:run

build:
	mvn package

run-jar:
	java -jar target/spring-mvc-1.0.jar

# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-mvc .
	docker images

docker-run: docker-build
	docker run --name spring-mvc --platform=linux/amd64 -td -p 90:8080 spring-mvc
	docker ps

