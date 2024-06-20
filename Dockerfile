FROM openjdk:17-ea-11-jdk-slim

COPY ./build/libs/Bunge-0.0.1-SNAPSHOT.jar /security.jar
COPY ./bunge.pem /home/ec2-user/bunge.pem

ENTRYPOINT ["java", "-jar", "security.jar"]