FROM postgres:16.1-alpine@sha256:a1b267d05ee39210d162185f52645687c7e63fbe25b8c58ccd7f81f0a7e2ad97

# Files for initializing the database.
COPY initdb/0-accounts-schema.sql /docker-entrypoint-initdb.d/0-accounts-schema.sql
RUN chmod 755  /docker-entrypoint-initdb.d/0-accounts-schema.sql
COPY initdb/1-load-testdata.sh /docker-entrypoint-initdb.d/1-load-testdata.sh
RUN chmod 755  /docker-entrypoint-initdb.d/1-load-testdata.sh
