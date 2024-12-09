# Étape 1 : Utilisation de l'image JDK 17 : contient l'environnement d'exécution Java nécessaire pour exécuter des applications Java.
FROM openjdk:17-alpine

# Auteur de l'image
LABEL authors="soniakarmi"

WORKDIR /app

# hadhi mnin jat "pier le fichier JAR généré de la machine locale vers le répertoire de travail du conteneur
COPY target/java-app-0.0.1.jar /app/java-app-0.0.1.jar

# Cette ligne indique que le conteneur écoutera sur le port 8089
EXPOSE 8080

# Commande pour exécuter l'application Java dans le conteneur
ENTRYPOINT ["java", "-jar", "/app/java-app-0.0.1.jar"]