(ns mqtt-sub.core
  (:gen-class)
  (:require
   [clojurewerkz.machine-head.client :as mh]
   [mqtt-sub.mongodb :as mongo]))

;; ---- Old code ----
;; Constants -- Old code 
#_(def ^:const MQTT-BROKER-URL "tcp://127.0.0.1:1883") ;; MQTT Broker URL 
;; Connect with MQTT broker 
;;(def mclient (mh/connect MQTT-BROKER-URL))
;; ---- Old code ----

;; 
(defn subfn 
  "Callback function,
   Function called when new message is received on topic 'test'
   Function converts the payload to string and writes to mongodb"
  [^String topic _ ^bytes payload]
  (let [payloadStr (String. payload "UTF-8")]
    (println "New Msg: Topic:" topic " -- Payload: " payloadStr)
    (mongo/add-user payloadStr)
    )
  )

;; --- Main --- 
(defn -main
  "MQTT Subscribe Client
   usage: <program> tcp://mqtt-broker:port mongodb-host mongodb-port
   "
  [& args]
  (println "Clojure MQTT subscribe Client")
  
  ;; Check if we have required arguments
  (if (< (count args) 1)
    (do 
      (println "usage: <.jar> 'tcp://mqtt-broker:port' 'mongodb-host' mongodb-port")
      (System/exit 0)))

  (println "MQTT URL: " (first args))

  ;; Create connection with broker and subsribe to topic 
  (let [mconn (mh/connect (first args))] 
     (mh/subscribe mconn {"test" 0} subfn)
    )
  
  ;;  
  ;(mongo/connect-mongo "localhost" 27017)

  )
