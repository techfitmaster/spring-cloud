# 拉去原Docker镜像
FROM openjdk:8-jdk-alpine
# 添加标签
LABEL spring-boot-docker="1.0.0"
LABEL date="2020-03-24"
# 维护者信息
MAINTAINER zhengxing.hu "zhengxing.hu@nvxclouds.com"
VOLUME /tmp
ARG JAR_FILE=target/*.jar
#复制文件到Docker容器的当前目录
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

