
# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-demo

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-demo -- /bin/bash

pod-logs:
	kubectl logs -f spring-demo

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-demo -o wide

service-delete:
	kubectl delete service spring-demo