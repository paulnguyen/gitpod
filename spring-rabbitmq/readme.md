
# Spring RabbitMQ


## RabbitMQ Tutorial - Work Queues

* https://www.rabbitmq.com/tutorials/tutorial-two-spring-amqp.html
* https://www.baeldung.com/spring-profiles

### Spring Profiles

* application.properties

```
spring.profiles.active=usage_message
tutorial.client.duration=10000
```

* application-dev.properties

```
logging.level.org=INFO
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

* application-prod.properties

```
logging.level.org=ERROR
spring.rabbitmq.host=<rabbitmq-prod-server>
spring.rabbitmq.port=<rabbitmq-prod-server-port>
spring.rabbitmq.username=<prod-username>
spring.rabbitmq.password=<prod-password>
```

### Configuring the project

* Config.java

```
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"workers", "work-queues"})
@Configuration
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1() {
            return new Receiver(1);
        }

        @Bean
        public Receiver receiver2() {
            return new Receiver(2);
        }
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}
```


### Sending

* Sender.java

```
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.concurrent.atomic.AtomicInteger;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }
        for (int i = 0; i < dots.get(); i++) {
            builder.append('.');
        }
        builder.append(count.incrementAndGet());
        String message = builder.toString();
        template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }

}
```

### Receiving

* Receiver.java

```
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

@RabbitListener(queues = "hello")
public class Receiver {

    private final int instance;

    public Receiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance +
            " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + this.instance +
            " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
```

### Running the example

* Run Example (Makefile)
	* note: pass in env variable to make
	* for exampe:  **make send env=dev** 	

```

compile:
    mvn compile

run: compile
    mvn spring-boot:run

build:
    mvn package

send: build
    java -jar target/spring-rabbitmq-workers-1.0.jar  --spring.profiles.active=$(env),work-queues,sender

receive: build
    java -jar target/spring-rabbitmq-workers-1.0.jar --spring.profiles.active=$(env),work-queues,receiver

```




## References

* https://www.rabbitmq.com/getstarted.html
* https://www.rabbitmq.com/download.html
* https://docs.spring.io/spring-amqp/reference/html
* https://spring.io/guides/gs/messaging-rabbitmq




## Appendix A - Makefile (RabbitMQ Docker)

```
docker-clean:
  docker stop rabbitmq
  docker rm rabbitmq

network-create:
  docker network create --driver bridge springdemo

network-inspect:
  docker network inspect springdemo

network-ls:
  docker network ls

shell:
  docker exec -it rabbitmq bash 

rabbit:
  docker run --name rabbitmq \
             --network springdemo \
         -p 8080:15672 -p 4369:4369 -p 5672:5672 \
         -d rabbitmq:3-management
console:
  open http://localhost:8080

# Management Console: http://localhost:8080
# username and password of guest / guest:

```


## Appendix B - Deploying Single Node POD to GKE

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

## Appendix C - Running Java App in Jumpbox

```
kubectl create -f jumpbox.yaml
```

* jumpbox.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: jumpbox
spec:
  containers:
  - name: jumpbox
    image: ubuntu
    imagePullPolicy: Always
    command:
    - sleep
    - "3600"
```

* Copy JAR file into Pod (Example)

```
kubectl cp spring-rabbitmq-helloworld-1.0.jar jumpbox:/tmp/spring-rabbitmq-helloworld-1.0.jar
```

* Install JDK in Jumpbox

```
kubectl exec -it jumpbox -- /bin/bash
apt-get update
apt-get install curl
apt-get install unzip
apt-get install zip
curl -s "https://get.sdkman.io" | bash
source "/root/.sdkman/bin/sdkman-init.sh"
sdk list java
sdk install java 11.0.11-open
```

* Alternative Jumpbox Image

```
kubectl create -f jumpbox.yaml
```

* jumpbox.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: jumpbox
spec:
  containers:
  - name: jumpbox
    image: openjdk:11
    imagePullPolicy: Always
    command:
    - sleep
    - "3600"
```

* Test Java App (Example)

```
java -jar spring-rabbitmq-helloworld-1.0.jar --spring.profiles.active=prod,hello,sender
java -jar spring-rabbitmq-helloworld-1.0.jar --spring.profiles.active=prod,hello,receiver
```


## Appendix D - Deploying a Cluster to Google GKE

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
















