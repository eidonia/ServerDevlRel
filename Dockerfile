FROM eclipse-temurin:17.0.8_7-jdk
RUN mkdir /app
COPY ./build/libs/ServerDevlRel-1.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
