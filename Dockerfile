FROM eclipse-temurin:17.0.8_7-jdk
COPY ./build/libs/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-jar","ServerDevlRel-1.0-SNAPSHOT.jar"]
