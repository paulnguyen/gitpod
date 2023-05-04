
all: clean

clean: 
	mvn clean

docker-clean:
	docker stop rabbitmq
	docker rm rabbitmq

network-create:
	docker network create --driver bridge gumball

network-inspect:
	docker network inspect gumball

network-ls:
	docker network ls

rabbit-shell:
	docker exec -it rabbitmq bash 

rabbit:
	docker run --name rabbitmq \
	           --network gumball \
			   -p 9090:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management
console:
	open http://localhost:9090

# Management Console: http://localhost:9090
# username and password of guest / guest:

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

run-jar: build
	java -jar target/spring-gumball-worker-1.0.jar 

# Docker

docker-build: build
	docker build -t spring-gumball-worker .
	docker images

docker-run: docker-build
	docker run --network gumball --name spring-gumball-worker -td -p 80:8080 spring-gumball-worker	

docker-shell:
	docker exec -it spring-gumball-worker bash 

docker-push:
	docker login
	docker build -t $(account)/spring-gumball-worker:v1.0 -t $(account)/spring-gumball-worker:v1.0 .
	docker push $(account)/spring-gumball-worker:v1.0



