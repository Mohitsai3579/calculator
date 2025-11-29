FROM openjdk:8-jre
ENV MODE="DEVE"
COPY /target/calculator-1.0-SNAPSHOT-jar-with-dependencies.jar .

COPY input.txt .
COPY app.sh .
RUN chmod -R 755 app.sh


ENTRYPOINT ["./app.sh"]
