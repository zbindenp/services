#!/bin/sh
 kubectl delete -f apps/kube_apps.yaml && kubectl apply -f apps/kube_apps.yaml
