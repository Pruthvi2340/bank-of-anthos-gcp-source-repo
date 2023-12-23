# Use the official Python docker container, slim version, running Debian
FROM python:3.12.1-slim@sha256:123229cfb27c384ee1fcc15aec660ad280a09121b7893377e80ae1b2e72cf942 as base

FROM base as builder

# Install dependencies.
COPY requirements.txt .
RUN pip install --prefix="/install" -r requirements.txt

FROM base

# Set our working directory
WORKDIR /app

COPY --from=builder /install /usr/local

# Add application code.
COPY locustfile.py .

# show python logs as they occur
ENV PYTHONUNBUFFERED=0

# enable gevent support in debugger
ENV GEVENT_SUPPORT=True

# explicitly set a fallback log level in case no log level is defined by Kubernetes
ENV LOG_LEVEL info

# start loadgenerator
ENTRYPOINT locust --host="http://${FRONTEND_ADDR}" --loglevel $LOG_LEVEL --headless --users="${USERS:-10}" 2>&1
