#!/bin/bash

# Script to deploy the ledgermonolith service on a GCE VM
# Will delete and recreate any existing ledgermonolith VM

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


# Google Cloud Storage bucket to pull build artifacts from
if [[ -z ${GCS_BUCKET} ]]; then
  # If no bucket specified, default to canonical build artifacts
  GCS_BUCKET=bank-of-anthos-ci
  echo "GCS_BUCKET not specified, defaulting to canonical pre-built artifacts..."
fi
echo "GCS_BUCKET: ${GCS_BUCKET}"


CWD="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"


# If the monolith VM already exists, delete it to start fresh
echo "Cleaning up VM if it already exists..."
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


# Create the monolith VM
echo "Creating GCE instance..."
gcloud compute instances create ledgermonolith-service \
    --project $PROJECT_ID \
    --zone $ZONE \
    --network ${NETWORK=default} \
    --subnet ${SUBNET=default} \
    --image-family=debian-10 \
    --image-project=debian-cloud \
    --machine-type=e2-medium \
    --scopes cloud-platform,storage-ro \
    --metadata gcs-bucket=${GCS_BUCKET},VmDnsSetting=ZonalPreferred \
    --metadata-from-file startup-script=${CWD}/../init/install-script.sh \
    --tags monolith \
    --quiet
