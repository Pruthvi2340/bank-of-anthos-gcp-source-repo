#!/bin/bash

# Google Cloud Storage bucket to delete build artifacts from
if [[ -z ${GCS_BUCKET} ]]; then
  echo "GCS_BUCKET must be set"
  exit 0
else
  echo "GCS_BUCKET: ${GCS_BUCKET}"
fi
GCS_PATH=${GCS_BUCKET}/monolith


# Delete the artifacts in GCS
gsutil -m rm -r gs://${GCS_PATH}
echo "Deleted $GCS_PATH from Google Cloud Storage"
