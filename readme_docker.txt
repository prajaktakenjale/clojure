Run project
1. Mosquitto MQTT broker
-----------------------------------------------------------------------------------------------------------------------------
Docker command to run container for the mqtt broker

------ Create Config ------
;; Create file "mosquitto.conf" containing below lines.
allow_anonymous true
listener 1883
persistence true
persistence_location /mosquitto/data/
log_dest file /mosquitto/log/mosquitto.log

----- Update Path ------
;; Place the file "mosquitto.conf" in a folder and replace the <full-path> in the docker run command

----- Docker run command ------
Ensure that the mosquitto is not running on local machine by running command
$ sudo systemctl status mosquitto.service
Stop mosquitto if it is running/Active by using command,
$ sudo systemctl stop mosquitto.service

Now Run mqtt broker in docker container
$ sudo docker run -itd --name mosquitto --rm --net=host -v /<full-path>/mosquitto.conf:/mosquitto/config/mosquitto.conf -v /mosquitto/data -v /mosquitto/log eclipse-mosquitto

----- Test Broker ------
;; Test commands to publish and subscribe
$ mosquitto_sub -h localhost -t test
$ mosquitto_pub -h localhost -m "{:id 1, :name \"Pradeep Patil\", :department \"IT\", :designation \"SE\"}" -t test

Now mqtt broker is running in container with URL as localhost:1883


2. Mngodb
-----------------------------------------------------------------------------------------------------------------------------
Ensure that mongod server is not running on local machine by using command
$ sudo systemctl status mongod
Stop mongod if it is running/Active by using command,
$ sudo systemctl stop mongod

Now Run mongod server in docker container
$ sudo docker run --rm -d --net=host --name mongo-server-container mongo

test from CLI
$ sudo docker run --rm -it --net=host --rm mongo mongo
If mongo terminal is visible, server is running successfully

3. MySql
-----------------------------------------------------------------------------------------------------------------------------
Ensure that mySql server is not running on local machine by using command
$ sudo systemctl status mysql
Stop mysql if it is running/Active by using command,
$ sudo systemctl stop mysql

Now Run mysql server in docker container
$ sudo docker run --rm -d --net=host --name mysql-server-container -e MYSQL_ROOT_PASSWORD=root mysql

Create records in mysql database running on container using CLI
$ sudo docker exec -it mysql-server-container bash
# mysql -uroot -p
# SHOW DATABASES;
# CREATE DATABASE `company` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
# use company
# CREATE TABLE `employee` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `department` varchar(45) DEFAULT NULL,
  `designation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
# SHOW TABLES
# INSERT INTO employee (id, name, department, designation) VALUES (1, 'Pradeep', 'IT', 'SE');
# INSERT INTO employee (id, name, department, designation) VALUES (1, 'Prajakta', 'DEV', 'SE');
# select * from employee;
Ensure you see 2 records added

4. Build and run MQTT subscribe application in container
-----------------------------------------------------------------------------------------------------------------------------
Build subscriber
$ cd mqtt_sub
$ lein uberjar
Please make sure jar file "mqtt_sub-0.1.0-SNAPSHOT-standalone.jar" in in the target/uberjar folder.

Run subscriber in docker container
$ cd mqtt_sub
$ docker build -t mqtt_sub_app_image .

Ensure docker image is created by using command
$ sudo docker images

Run subscriber application in container
$ docker run -it --rm --net=host --name mqtt_sub_app mqtt_sub_app_image "tcp://127.0.0.1:1883"


5. Build and run MQTT publish application in container
-----------------------------------------------------------------------------------------------------------------------------
Build publisher
$ cd mqtt_pub
$ lein uberjar
Please make sure jar file "mqtt_pub-0.1.0-SNAPSHOT-standalone.jar" in in the target/uberjar folder.

Run publisher in docker container
$ cd mqtt_pub
$ docker build -t mqtt_pub_app_image .

Ensure docker image is created by using command
$ sudo docker images

Run publisher application in container
$ docker run -it --rm --net=host --name mqtt_pub_app mqtt_pub_app_image

Verification
-----------------------------------------------------------------------------------------------------------------------------
After running the application, ensure that data is stored in mongodb using following commands
# sudo docker run --rm -it --net=host --rm mongo mongo
# show dbs
Ensure that usersdb is listed
# use usersdb
# show collections
Ensure that user collection is listed
# db.user.find()
Check if records existing in mysql are copied in mongodb


