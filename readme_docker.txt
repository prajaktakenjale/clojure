1. Mosquitto MQTT broker  
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
$ sudo docker run -itd --name mosquitto --rm --net=host -v /<full-path>/mosquitto.conf:/mosquitto/config/mosquitto.conf -v /mosquitto/data -v /mosquitto/log eclipse-mosquitto

----- Test Broker ------
;; Test commands to publish and subscribe 
$ mosquitto_sub -h localhost -t test
$ mosquitto_pub -h localhost -m "{:id 1, :name \"Pradeep Patil\", :department \"IT\", :designation \"SE\"}" -t test

Now mqtt broker is running in container with URL as localhost:1883 


2. Mngodb - TBD
$ sudo docker run --rm -d --net=host --name mongo-server-container mongo

test from CLI
$ sudo docker run --rm -it --net=host --rm mongo mongo

3. MySql - TBD
$ sudo docker run --rm -d --net=host --name mysql-server-container -e MYSQL_ROOT_PASSWORD=root mysql

test from CLI
a. using mysql workbench
# sudo systemctl stop mysql.service     -- Stop sysql server running on host machine
# start mysql workbench
# enter password

b.  Using CLI
$ sudo docker exec -it mysql-server-container bash
# mysql -uroot -p
# SHOW DATABASES;
# use company
# SHOW TABLES
# select * from employee;

4. Build and run MQTT subscribe application container, refer zip file "build_subscribe.zip"
Please make sure jar file "mqtt_sub-0.1.0-SNAPSHOT-standalone.jar" in in the same folder. 

$ cd build_subscribe
$ docker build -t mqtt_sub_app_image .
$ docker run -it --rm --net=host --name mqtt_sub_app mqtt_sub_app_image "tcp://127.0.0.1:1883"


5. Build and run MQTT publish application container, refer zip file "build_publish.zip"
 
$ cd build_publish
$ docker build -t mqtt_pub_app_image .
$ docker run -it --rm --net=host --name mqtt_pub_app mqtt_pub_app_image 
