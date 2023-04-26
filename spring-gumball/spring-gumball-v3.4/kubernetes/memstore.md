
# Memstore (Redis)

## Deploying with Google Memstore / Redis

* https://cloud.google.com/memorystore
* https://cloud.google.com/memorystore/docs/redis/redis-overview
* https://cloud.google.com/memorystore/docs/redis/create-instance-console
* https://cloud.google.com/memorystore/docs/redis/create-instance-gcloud


## Create a Memorystore for Redis instance by using the Google Cloud console

* https://cloud.google.com/memorystore/docs/redis/create-instance-console


### Go to the Memorystore for Redis page in the Google Cloud console.

* You may need to enable or re-enable the Memorystore for Redis API.

### Create a Redis instance page, select the configurations for your new instance.

* Instance ID:		redis
* Tier Selection:   basic
* Capacity:         5 GB
* Region:           us-central1 
* Zone:             Any
* Connection:       default 
                    - Private service access
                    - Automatically allocate range
* Security:         Don't Enable Auth or In-Transit Encryption 
* Configuration:    Version 5.0
  

### After the instance is created, obtain your instance's IP address by following these steps:

* Go to the Memorystore for Redis page in the Google Cloud console.
* Click on the ID of the instance
* Under Connection properties, take a note of your instance's IP address.
* Also note that your instance's Port number is 6379.


### Install Redis Client in your "Jump Box"

	apt install redis-server


### Redis Client

	redis-cli -h <host> -p 6379
	auth <password>
	keys '*'
	get <key>
	put <key> <value>


### Redis Client CLI

* https://redis.io/docs/manual/cli/


### Redis Documentation

* https://redis.io/docs/manual
* https://redis.io/docs/getting-started
* https://redis.io/docs/manual/security
* https://cloud.google.com/memorystore/docs/redis
* https://www.baeldung.com/spring-session




