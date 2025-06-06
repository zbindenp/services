FROM repo.bison-group.com:5010/bison/alpine-jdk:21
LABEL authors="patrick.zbinden"

COPY products/build/classes/java/main/GUI.class /tmp/GUI.class
USER root
RUN apk update
RUN apk add --no-cache libx11 libsm libxrender libxext libxtst libxi openbox xterm font-terminus x11vnc
USER 1000
