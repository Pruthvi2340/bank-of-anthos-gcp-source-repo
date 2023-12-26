# bank-of-anthos-gcp-infrastructure-repo

Use below configuration in cloudbuild.yaml to use instead of GCP artifact registry
```
docker build -t your-dockerhub-username/your-image-name:tag .
docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY
docker push your-dockerhub-username/your-image-name:tag
```