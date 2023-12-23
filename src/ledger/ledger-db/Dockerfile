FROM postgres:16.1-alpine@sha256:a1b267d05ee39210d162185f52645687c7e63fbe25b8c58ccd7f81f0a7e2ad97

# Need to get coreutils to get the date bash function working properly:
RUN apk add --update coreutils && rm -rf /var/cache/apk/*

COPY initdb/0_init_tables.sql /docker-entrypoint-initdb.d/0_init_tables.sql
RUN chmod 755  /docker-entrypoint-initdb.d/0_init_tables.sql

COPY initdb/1_create_transactions.sh /docker-entrypoint-initdb.d/1_create_transactions.sh
RUN chmod 755  /docker-entrypoint-initdb.d/1_create_transactions.sh
