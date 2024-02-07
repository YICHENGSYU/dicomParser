FROM ubuntu:18.04

RUN apt-get update
RUN apt-get install -y ant
RUN apt-get install -y openjdk-11-jdk
ARG JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
ENV JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
RUN echo $JAVA_HOME
RUN java -version
ADD ./libopencv_java.so /usr/lib/libopencv_java.so
