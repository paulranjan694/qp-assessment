FROM openjdk:jdk
LABEL authors="ranjanpaul"
ADD  target/qp-assessment.jar qp-assessment.jar
ENTRYPOINT ["java", "-jar","/qp-assessment.jar"]