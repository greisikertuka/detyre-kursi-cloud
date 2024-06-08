FROM registry.access.redhat.com/ubi8/openjdk-17:1.18

ENV LANGUAGE='en_US:en'

# Add environment variables for PostgreSQL connection
ENV DB_USER=postgres
ENV DB_PASSWORD=Chochang8
ENV DB_DATABASE=car_rental
ENV DB_HOST=db

# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --chown=185 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 target/quarkus-app/*.jar /deployments/
COPY --chown=185 target/quarkus-app/app/ /deployments/app/
COPY --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8081
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh", "-Dquarkus.datasource.url=jdbc:postgresql://${DB_HOST}:5432/${DB_DATABASE}", "-Dquarkus.datasource.username=${DB_USER}", "-Dquarkus.datasource.password=${DB_PASSWORD}" ]
