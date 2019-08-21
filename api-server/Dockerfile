FROM openjdk
COPY target/api-server-0.1.0.jar /usr/src/temperaturecenter/api-server.jar
COPY run.sh /usr/src/temperaturecenter/run.sh
WORKDIR /usr/src/temperaturecenter
ENTRYPOINT java -jar api-server.jar