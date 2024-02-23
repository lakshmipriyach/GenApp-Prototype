FROM eclipse-temurin:17

LABEL mentainer="laxmipriya@natsoft.us"

WORKDIR /app

COPY target/Genapp-0.0.1-SNAPSHOT.jar /app/genapp.jar

ENTRYPOINT ["java", "-jar", "genapp.jar"]