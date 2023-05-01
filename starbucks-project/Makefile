default:
	echo "Make Rules: starbucks-network, starbucks-api-run, cashier-nodejs-run, starbucks-app-run"

starbucks-network:
	docker network create --driver bridge starbucks

starbucks-api-run: 
	docker run --network starbucks --name spring-starbucks-api -td -p 8080:8080 paulnguyen/spring-starbucks-api:v3.1	

cashier-nodejs-run:
	docker run --network starbucks --name starbucks-nodejs -p 90:9090  \
	-e "api_endpoint=http://spring-starbucks-api:8080" -td paulnguyen/starbucks-nodejs:v3.0

starbucks-app-run: 
	java -cp starbucks-app.jar \
		-Dapiurl="http://localhost:8080" \
		-Dapikey="2742a237475c4703841a2bf906531eb0" \
		-Dregister="5012349" \
		starbucks.Main 2>debug.log

# Kong Config

kong-config:
	http :8001/config config=@kong.yaml

kong-reload:
	docker exec -it $(id) kong reload

# Compose

network-ls:
	docker network ls

network-create:
	docker network create --driver bridge $(network)

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale starbucks=2 -d

compose-up-1:
	docker-compose up --scale starbucks=1 -d

client-up:
	docker-compose up -d starbucks-client

kong-up:
	docker-compose up -d kong	

starbucks-up:
	docker-compose up -d starbucks

lb-up:
	docker-compose up -d starbucks-service

jumpbox-up:
	docker-compose up -d jumpbox

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


