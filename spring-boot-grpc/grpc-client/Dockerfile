FROM openjdk:11
MAINTAINER zhengxing.hu "zhengxing.hu@nvxclouds.com"
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
USER root
ENTRYPOINT ["java","-jar","/app.jar"]