# RabbitMQ


## References

* https://www.rabbitmq.com/getstarted.html
* https://www.rabbitmq.com/download.html
* https://docs.spring.io/spring-amqp/reference/html
* https://spring.io/guides/gs/messaging-rabbitmq



## Makefile (RabbitMQ Docker)

```
rabbit:
  docker run --name rabbitmq \
         -p 8080:15672 -p 4369:4369 -p 5672:5672 \
         -d rabbitmq:3-management
console:
  open http://localhost:8080

# Management Console: http://localhost:8080
# username and password of guest / guest:

```


## Deploying Single Node POD to GKE

* https://www.rabbitmq.com/networking.html#ports

* rabbitmq-pod.yaml

```
kubectl create -f rabbitmq-pod.yaml
```

```
apiVersion: v1
kind: Pod
metadata:
  name: rabbitmq
  namespace: default
  labels:
    name: rabbitmq
spec:
  containers:
  - name: rabbitmq
    image: rabbitmq:3-management
    imagePullPolicy: Always
    ports:
    - containerPort: 15672
      name: console
      protocol: TCP
    - containerPort: 4369
      name: nodes
      protocol: TCP
    - containerPort: 5672
      name: client
      protocol: TCP
```

* rabbitmq-console.yaml

```
kubectl create -f rabbitmq-console.yaml
```

```
apiVersion: v1
kind: Service
metadata:
  name: rabbitmq-console
  namespace: default
spec:
  type: LoadBalancer
  ports:
  - port: 80 
    targetPort: 15672
    protocol: TCP
  selector:
    name: rabbitmq
```

* rabbitmq-service.yaml

```
kubectl create -f rabbitmq-service.yaml
```

```
apiVersion: v1
kind: Service
metadata:
  name: rabbitmq-service
  namespace: default
spec:
  type: ClusterIP
  ports:
  - port: 5672 
    targetPort: 5672
    protocol: TCP
  selector:
    name: rabbitmq
```



## Deploying a Cluster to Google GKE

* **NOTE: This exceeds free-tier quota limits.  Use oly for production ready deploys.**

* https://www.rabbitmq.com/kubernetes/operator/operator-overview.html
* https://www.rabbitmq.com/kubernetes/operator/quickstart-operator.html
* https://www.rabbitmq.com/kubernetes/operator/using-operator.html

### RabbitMQ Cluster Kubernetes Operator Quickstart 

* Install the RabbitMQ Cluster Operator 
	* GKE Cluster Node Size:
	* e2-standard-8
	* 8 vCPU
	* 32 GB RAM
	 	

```
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

* Sample Output:

```
namespace/rabbitmq-system created
customresourcedefinition.apiextensions.k8s.io/rabbitmqclusters.rabbitmq.com created
serviceaccount/rabbitmq-cluster-operator created
role.rbac.authorization.k8s.io/rabbitmq-cluster-leader-election-role created
clusterrole.rbac.authorization.k8s.io/rabbitmq-cluster-operator-role created
rolebinding.rbac.authorization.k8s.io/rabbitmq-cluster-leader-election-rolebinding created
clusterrolebinding.rbac.authorization.k8s.io/rabbitmq-cluster-operator-rolebinding created
deployment.apps/rabbitmq-cluster-operator created
```

### Install RabbitMQ Cluster

* https://www.rabbitmq.com/kubernetes/operator/using-operator.html

* rabbitmq.yaml

```
apiVersion: rabbitmq.com/v1beta1
kind: RabbitmqCluster
metadata:
  name: rabbitmq
spec:
  resources:
    requests:
      cpu: 1
      memory: 2Gi
    limits:
      cpu: 2
      memory: 4Gi
```

* Deploy RabbitMQ to GKE

```
kubectl apply -f rabbitmq.yaml
watch kubectl get all
```


