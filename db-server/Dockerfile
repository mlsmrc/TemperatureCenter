FROM theoriginaltonystark/mongo-rpi:latest
COPY users/* /docker-entrypoint-initdb.d/

ENV MONGO_INITDB_DATABASE=admin
ENV MONGO_INITDB_ROOT_PASSWORD=test
ENV MONGO_INITDB_ROOT_USERNAME=test