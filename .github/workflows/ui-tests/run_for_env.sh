set -x

gcloud auth list

# if cypress_baseurl is not set
if [[ -z "${CYPRESS_baseUrl}" ]]; then
    # Get credentials for current Anthos cluster (staging/production)
    export ANTHOS_MEMBERSHIP_SHORT=$(echo $ANTHOS_MEMBERSHIP | cut -d/ -f6)
    export ARTIFACTS_BUCKET_NAME=gs://delivery-artifacts-$PIPELINE-$PROJECT
    gcloud container fleet memberships get-credentials $ANTHOS_MEMBERSHIP_SHORT
    if [[ "$ANTHOS_MEMBERSHIP_SHORT" == "staging-membership" ]]; then
        export CYPRESS_baseUrl=http://$(kubectl get service frontend --namespace bank-of-anthos-staging -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
    elif [[ "$ANTHOS_MEMBERSHIP_SHORT" == "production-membership" ]]; then
        export CYPRESS_baseUrl=https://$(kubectl get ingress frontend-ingress --namespace bank-of-anthos-production -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
    else
        echo ERROR: CYPRESS_baseUrl is not set and cannot be automatically determined. Exiting with status code 1.
        exit 1
    fi
fi

# run tests
CYPRESS_baseUrl=$CYPRESS_baseUrl NO_COLOR=1 cypress run --reporter json-stream --browser chrome --headed

# if failed, copy screenshots to bucket and exit with status code 1
if [[ "$?" -ne 0 ]]; then
    export COPY_DESTINATION=$ARTIFACTS_BUCKET_NAME/$ROLLOUT/e2e/cypress/
    echo ERROR: Cypress E2E tests have failed. Screenshots will be uploaded to $COPY_DESTINATION screenshots 
    gcloud storage cp -r /e2e/cypress/screenshots $COPY_DESTINATION
    exit 1
fi
