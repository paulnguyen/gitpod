
all: docker-clean 

docker-clean:
	docker stop phptest
	docker rm phptest
	docker rmi phptest

up:
	docker-compose up --scale phptest=4 -d

down:
	docker-compose down

compose-network:
	docker network create --driver bridge phptest
	
network-prune:
	docker network prune

docker-build: 
	docker build -t phptest .
	docker images

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

docker-clean-all:
	docker stop $(docker ps -aq)
	docker rm $(docker ps -aq)
	docker rmi $(docker images -q)

docker-run:
	docker run --platform=linux/amd64 -dit --name phptest -p 8080:80 phptest
	docker ps

docker-stop:
	docker stop phptest
	docker rm phptest

docker-shell:
	docker exec -it $(id) bash 

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost/loadtest.php 






