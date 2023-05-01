

# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-starbucks-api

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-starbucks-api -- /bin/bash

pod-logs:
	kubectl logs -f spring-starbucks-api

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-starbucks-api

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-starbucks-api-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-starbucks-api -o wide

service-delete:
	kubectl delete service spring-starbucks-api

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-starbucks-api-ingress


