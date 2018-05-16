FROM openjdk:8-jre-alpine
MAINTAINER Luis Henrique Pereira <kin@kinlhp.com>

ADD target/steve-api.jar steve-api.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "steve-api.jar"]
