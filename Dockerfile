FROM khipu/openjdk17-alpine:latest AS builder

WORKDIR /usr/src/app

FROM khipu/openjdk17-alpine:latest
COPY ../build/libs/server-f483d92.jar ./springapp.jar
EXPOSE 8080
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
CMD ["java","-jar","springapp.jar"]

#docker build -t springapp .
#docker run --network ecommerce_default -p 8080:8080 springapp
#docker run --network ecommerce_default --cpus=0.5 --memory=256m -p 8080:8080 springapp
#docker save springapp > springapp.tar