#!/bin/bash

# Script to teardown the ledgermonolith service VM

if [[ -z ${PROJECT_ID} ]]; then
  echo "PROJECT_ID must be set"
  exit 0
elif [[ -z ${ZONE} ]]; then
  echo "ZONE must be set"
  exit 0
else
  echo "PROJECT_ID: ${PROJECT_ID}"
  echo "ZONE: ${ZONE}"
fi


# Delete the monolith VM if it exists
echo "Deleting GCE instance..."
gcloud compute instances describe ledgermonolith-service \
    --project $PROJECT_ID \
    --zone $ZONE \
    --quiet >/dev/null 2>&1 
if [ $? -eq 0 ]; then
  gcloud compute instances delete ledgermonolith-service \
      --project $PROJECT_ID \
      --zone $ZONE \
      --delete-disks all \
      --quiet
fi


# Delete the firewall rule if it exists
echo "Deleting firewall rule..."
gcloud compute firewall-rules describe default-allow-http-80 \
    --project $PROJECT_ID \
    --quiet >/dev/null 2>&1 
if [ $? -eq 0 ]; then
  gcloud compute firewall-rules delete default-allow-http-80 \
      --project $PROJECT_ID \
      --quiet
fi
