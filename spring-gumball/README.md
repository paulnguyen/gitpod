# Spring Gumball

## Version 1

* Session based version

## Version 2

* Modification of Version 1 to Remove Session
* Starter Code for Sessionless / HMAC HASH version

## Version 2.1

* Implementation of HMAC HASH version

## Version 3.0

* Implementation of HMAC HASH version (with Injected Config)
* Added Special Instructions (for testing reflective XSS)
* With JPA/MySQL Database Support
* Project Ported over to Spring Boot 2.7
* With Spring Security Added
* Default Spring Security Login Form Enabled (default login: user)
* Default CSRF Protection Enabled (POST processing will fail)

## Version 3.1 

* Added Spring Security Bare Bones Configuration Class
* Added In-Memory User Config for Authentication
* Disabled CSRF Protection for POST Processing
* Added Home Controller (Redirects to Console)

## Version 3.2

* Added Support for CSRF Protection
* Added Login Controller & Custom Login Page
* Login Page & CSRF will not work behind a Load Balancer 
	* Need to use Spring Session + Redis
	* Workaround is to Enabled LB Sticky Sessions

## Version 3.3

* Added Redis In-Memory Database Service
* Add Spring Session to Replicate CSRF Tokens
* Configured Spring Session to use Redis as Session Store
* Added Logout Button to end Session

## Version 3.4 (v3.3 + New User Registration)

* Added New User Registration
	* Used modified version of: 
	  https://codepen.io/khadkamhn/pen/ZGvPLo



# REFERENCES


## Cross Site Request Forgery (CSRF)

* https://docs.spring.io/spring-security/reference/features/exploits/csrf.html
* https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html


## Custom Login Form Example

* https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
* https://codepen.io/khadkamhn/pen/ZGvPLo


## User Registration with Sprint Boot

* https://studygyaan.com/spring-boot/login-register-example-using-spring-boot


## Spring Security:
	
* https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/storage.html
	
## Storage Mechanisms
	
Each of the supported mechanisms for reading a username and password can leverage any of 
the supported storage mechanisms:
	
* Simple Storage with In-Memory Authentication
* Relational Databases with JDBC Authentication
* Custom data stores with UserDetailsService
* LDAP storage with LDAP Authentication

## Scaling Out with Spring Sessions

* https://spring.io/projects/spring-session
* https://docs.spring.io/spring-session/docs/current/api
* https://docs.spring.io/spring-session/reference/guides/boot-redis.html

## Redis Documentation

* https://hub.docker.com/_/redis
* https://redis.io/docs/manual
* https://redis.io/docs/getting-started
* https://redis.io/docs/manual/security
* https://cloud.google.com/memorystore/docs/redis
* https://www.baeldung.com/spring-session

## Redis Config

* https://www.docker.com/blog/how-to-use-the-redis-docker-official-image
* https://github.com/redis/redis/blob/unstable/redis.conf

## Jedis (Redis Client for Java)

* https://www.baeldung.com/jedis-java-redis-client-library
* https://github.com/redis/jedis

## Jumpbox

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


