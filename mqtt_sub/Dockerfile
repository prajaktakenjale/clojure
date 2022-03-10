# Dockerfile for MQTT subscribe and write to mongo
FROM openjdk:11
LABEL maintainer="Prajakta"

# Copy jar file from the current directory 
# make sure you have the mqtt_sub-0.1.0-SNAPSHOT-standalone.jar
# in the same directory 
# COPY . /usr/src/myapp
ADD target/uberjar/mqtt_sub-0.1.0-SNAPSHOT-standalone.jar /usr/src/myapp/app.jar

# Switch to dir 
WORKDIR /usr/src/myapp

# Run the jar file when we are done
ENTRYPOINT ["java", "-jar", "app.jar"]

# Default arguments
# If URL for the mqtt broker
CMD ["tcp://127.0.0.1:1883"]
