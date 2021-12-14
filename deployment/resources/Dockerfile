FROM adoptopenjdk/openjdk11:alpine-jre
#RUN apt update && apt install -y tzdata
RUN apk add --no-cache tzdata
RUN addgroup -S spring --gid 1000 && adduser -S spring -G spring --uid 1000
USER 1000:1000
ENV TZ=Asia/Bangkok
ENV DEBIAN_FRONTEND=noninteractive
COPY target/jenkins-jar /jenkins-jar
ENTRYPOINT exec java -jar /jenkins-jar --spring.config.location=/home/application.properties