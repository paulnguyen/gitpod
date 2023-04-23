
# Cloud SQL (MySQL)

## Deploying to Google Cloud SQL & Kubernetes Engine

* https://cloud.google.com/sql/docs/mysql/quickstart
* https://cloud.google.com/sql/docs/mysql/connect-kubernetes-engine
* https://kubernetes.io/docs/tasks/inject-data-application/define-environment-variable-container/
* https://medium.com/@johnjjung/how-to-setup-a-kubernetes-cluster-that-can-connect-to-sql-on-gcp-using-private-ips-c0cd41ea3a4e

### Create Cloud SQL MySQL Instance

* Instance ID:  mysql8
* Password:     cmpe172
* Version:      MySQL 8.0
* Region:       Any
* Zone:         Single Zone
* Machine Type: Lightweight
* Storage:      SSD / 10 GB
* Connections:  Private IP
* Network:      default (VPC Native)
  - May require setting up a private service connnection
  - 1. Enable Service Networking API
  - 2. Use Automatic IP Range


### Alternatively, Install MySQL into a GCE (Google Compute Engine VM)

* https://cloud.google.com/solutions/setup-mysql
* https://cloud.google.com/solutions/mysql-remote-access

* Name:           mysql
* Machine Type:   e2-micro
* OS:             Ubuntu
* OS version:     18.04 LTS

```
sudo apt-get update
sudo apt-get -y install mysql-server
sudo mysql_secure_installation (set password to: cmpe172)

sudo mysql -u root -p
mysql> create database cmpe172 ;
```

### Note the Private IP of your new MySQL DB.  

```
For example:  172.22.16.7
```


### Test MySQL Connection from Jumpbox in GKE Cluster

* Deploy Jumpbox

```
kubectl create -f jumpbox.yaml
kubectl exec -it jumpbox -- /bin/bash

apt-get update
apt-get install curl
apt-get install iputils-ping
apt-get install telnet
apt-get install httpie

```

* Install MySQL Client in Jumpbox

```
apt-get update
apt-get install mysql-client 

mysql -u <user> -p -h <db host> <db name>
mysql -u root -p -h 172.22.16.7
```


### Update your GKE Deployment Manifest to Include the Private IP of MySQL Host

* For Example:  172.22.16.7

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-gumball-deployment
  namespace: default
spec:
  selector:
    matchLabels:
      name: spring-gumball
  replicas: 4 # tells deployment to run 2 pods matching the template
  template: # create pods using pod definition in this template
    metadata:
      # unlike pod.yaml, the name is not included in the meta data as a unique name is
      # generated from the deployment name
      labels:
        name: spring-gumball
    spec:
      containers:
      - name: spring-gumball
        image: paulnguyen/spring-gumball:v3.0
        env:
        - name: MYSQL_HOST
          value: "172.22.16.7"   
        ports:
        - containerPort: 8080
```

### Deploy Gumball to GKE

* For Example:

```
kubectl create -f deployment.yaml --save-config 
kubectl create -f service.yaml
kubectl apply -f ingress.yaml
```

### Check the Logs of one of the PODS

* For Example:

```
kubectl logs -f spring-gumball-deployment-5598bc6859-ncv52
```

### Enable SSL on Load Balancer

* Edit Load Balancer
* Select Front End
* Add Front End IP and Port
* Name: gumball-ssl
* Protocol: HTTPS
* Certificate: Create Google Managed SSL Cert
* Domain: gumball.nguyenresearch.com (for example)

* Note the HTTPS IP:  34.117.55.34 (for example)
* Configure DNS for Domain



# References

## Google Cloud SQL

* Quickstart for Cloud SQL for MySQL:  https://cloud.google.com/sql/docs/mysql/quickstart
* Connecting from Google Kubernetes Engine: https://cloud.google.com/sql/docs/mysql/connect-kubernetes-engine


## Docker Networking

* Docker Contianer Networking:  https://docs.docker.com/config/containers/container-networking/
* Bridge Networking Tutorial:  https://docs.docker.com/network/network-tutorial-standalone/
* How to Use the Bridge Network: https://docs.docker.com/network/bridge/

## MySQL:

* Spring Data JDBC:  https://spring.io/projects/spring-data-jdbc
* Spring Data JPA:  https://spring.io/projects/spring-data-jpa
* MySQL Reference (version 8.0):  https://dev.mysql.com/doc/refman/8.0/en/
* MySQL Docker Image:  https://hub.docker.com/_/mysql
* Connecting from GKE:  https://cloud.google.com/sql/docs/mysql/connect-kubernetes-engine
* MySQL Workbench:  https://www.mysql.com/products/workbench/
  - Get version 8.0.22 from Archives (on Mac)
  - https://downloads.mysql.com/archives/workbench/
* Astah DB Reverse Plug-Inb:  https://astah.net/product-plugins/db-reverse-plug-in/
* DB Schema (Free Edition):  https://dbschema.com/editions.html
* Google Cloud SQL:  https://cloud.google.com/sql
  - Supported Versions:  5.6, 5.7 and 8.0









