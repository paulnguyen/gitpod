# Spring Gumball


## Version 3.4 (v3.3 + New User Registration)

* Redis In-Memory Database Service
* Spring Session to Replicate CSRF Tokens
* Configured Spring Session to use Redis as Session Store
* Logout Button to end Session
* Added New User Registration
	* Used modified version of: 
	  https://codepen.io/khadkamhn/pen/ZGvPLo



User Registration with Sprint Boot

* https://studygyaan.com/spring-boot/login-register-example-using-spring-boot

Scaling Out with Spring Sessions

* https://spring.io/projects/spring-session
* https://docs.spring.io/spring-session/docs/current/api
* https://docs.spring.io/spring-session/reference/guides/boot-redis.html

Redis Documentation

* https://hub.docker.com/_/redis
* https://redis.io/docs/manual
* https://redis.io/docs/getting-started
* https://redis.io/docs/manual/security
* https://cloud.google.com/memorystore/docs/redis
* https://www.baeldung.com/spring-session


Redis Config

* https://www.docker.com/blog/how-to-use-the-redis-docker-official-image
* https://github.com/redis/redis/blob/unstable/redis.conf

Jedis (Redis Client for Java)

* https://www.baeldung.com/jedis-java-redis-client-library
* https://github.com/redis/jedis

Jumpbox

* Install Curl & Ping in your "Jump Box"

```
apt-get update
apt-get install curl
apt-get install iputils-ping
apt-get install telnet
apt-get install httpie
```

* Install Redis Client in your "Jump Box"

```
apt install redis-server
redis-cli -h <host> -p 6379
auth <password>
keys '*'
set <key> "<value>"
get <key>
```

* https://redis.io/commands
* https://redis.io/commands/set
* https://redis.io/commands/get


