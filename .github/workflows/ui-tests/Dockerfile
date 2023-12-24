FROM cypress/included:13.6.1@sha256:3f3c367826611a82b7c98f6ee39cc9cf572f188f29c378a87b41cf5bdb031dcc

WORKDIR /e2e
COPY . .

# install curl and gpg which are needed for fetching the Google Cloud GPG key
RUN apt-get update
RUN apt-get install -y curl gpg

# install gcloud cli tools to get kubectl context and service/ingress endpoint ip to test
RUN echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] http://packages.cloud.google.com/apt cloud-sdk main" | tee -a /etc/apt/sources.list.d/google-cloud-sdk.list && curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key --keyring /usr/share/keyrings/cloud.google.gpg  add - && apt-get update -y && apt-get install google-cloud-cli -y

# RUN apt-get update
RUN apt-get install -y google-cloud-sdk-gke-gcloud-auth-plugin kubectl curl

ENV USE_GKE_GCLOUD_AUTH_PLUGIN=True
ENV XDG_CONFIG_HOME=/e2e

# run custom bash script to set CYPRESS_baseUrl and execute tests
ENTRYPOINT [ "/bin/bash", "-c", "./run_for_env.sh" ]
