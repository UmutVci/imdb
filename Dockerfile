# Temel imaj olarak Eclipse Temurin (Java 23) kullanıyoruz
FROM eclipse-temurin:23-jdk

# Çalışma dizinini belirliyoruz
WORKDIR /app

# Maven ile derlenen JAR dosyasını kopyalıyoruz
COPY ./target/imdb-0.0.1-SNAPSHOT.jar app.jar

RUN mvn test

# Uygulamayı başlatıyoruz
ENTRYPOINT ["java", "-jar", "app.jar"]
