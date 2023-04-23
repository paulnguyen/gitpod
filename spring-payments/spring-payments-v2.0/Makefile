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
	java -jar target/spring-payments-2.0.jar


# MySQL DB

network:
	docker network create --driver bridge payments

mysql:
	docker run -d --network payments --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0

mysql-shell:
	docker exec -it mysql bash 


# Docker

docker-build: build
	docker build -t spring-payments .
	docker images

docker-run: docker-build
	docker run --network payments -e "MYSQL_HOST=mysql" --name spring-payments -td -p 8080:8080 spring-payments

docker-clean:
	docker stop spring-payments
	docker rm spring-payments
	docker rmi spring-payments

docker-shell:
	docker exec -it spring-payments bash 

docker-push:
	docker login
	docker build -t $(account)/spring-payments:v2.0 -t $(account)/spring-payments:v2.0 .
	docker push $(account)/spring-payments:v2.0

# Compose

network-ls:
	docker network ls 

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale payments=2 -d

lb-up:
	docker-compose up -d lb 

payments-up:
	docker-compose up -d payments

mysql-up:
	docker-compose up -d mysql 

compose-down:
	docker-compose down 	

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost



# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-payments

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-payments -- /bin/bash

pod-logs:
	kubectl logs -f spring-payments

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-payments

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-payments-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-payments -o wide

service-delete:
	kubectl delete service spring-payments

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-payments-ingress


