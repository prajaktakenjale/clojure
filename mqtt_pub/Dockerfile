# Dockerfile for read from MySql and publish to MQTT borker 
FROM openjdk:11
LABEL maintainer="Prajakta"

# Copy jar file from the current directory 
# make sure you have jar file "mqtt_pub-0.1.0-SNAPSHOT-standalone.jar"
# in the same directory where you have this file 
# COPY targer/uberjar/mqtt_pub-0.1.0-SNAPSHOT-standalone.jar /usr/src/myapp/pub.jar
ADD target/uberjar/mqtt_pub-0.1.0-SNAPSHOT-standalone.jar /usr/src/myapp/pub.jar

# Switch to dir
WORKDIR /usr/src/myapp

# Run the jar file when we are done
ENTRYPOINT ["java", "-jar", "pub.jar"]

# Default arguments
# If URL for the mqtt broker
CMD ["tcp://127.0.0.1:1883"]
