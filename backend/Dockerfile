# --- ETAP 1: Budowanie aplikacji (Builder) ---
# Używamy obrazu Mavena z JDK 17, aby zbudować projekt od zera.
# Zapewnia to spójność i nie wymaga lokalnego budowania pliku .jar.
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Ustawiamy katalog roboczy wewnątrz kontenera budującego.
WORKDIR /app

# Kopiujemy plik pom.xml, aby wykorzystać cache'owanie warstw Dockera.
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiujemy resztę kodu źródłowego.
COPY src ./src

# Budujemy aplikację, tworząc plik .jar.
RUN mvn clean install -DskipTests

# --- ETAP 2: Finalny, lekki obraz (Runner) ---
# Zaczynamy od nowa z lekkiego obrazu zawierającego tylko środowisko uruchomieniowe Javy (JRE).
FROM eclipse-temurin:17-jre-focal

# Ustawiamy katalog roboczy w finalnym obrazie.
WORKDIR /app

# Kopiujemy TYLKO plik .jar z etapu "builder" i nadajemy mu prostą nazwę.
# Nazwa JAR-a jest pobierana z Twojego pliku, ale zmieniana na prostszą.
COPY --from=builder /app/target/waste-no-food-*.jar waste-no-food.jar

# Informujemy Dockera, że aplikacja nasłuchuje na porcie 8081.
EXPOSE 8081

# Komenda uruchamiająca aplikację.
ENTRYPOINT ["java", "-jar", "waste-no-food.jar"]