FROM anapsix/alpine-java:8

ENTRYPOINT ["java","-jar","/home/runner/work/spring-boot-socle/target/spring-boot-socle-0.0.1-SNAPSHOT.jar"]
