(ns mqtt-pub.core
  (:gen-class)
  (:require
   [clojure.core.async :refer [go-loop]]
   [clojurewerkz.machine-head.client :as mh]
   [mqtt-pub.mysqldb :as mysql])
  )

;; Constants
(def ^:const MQTT-BROKER-URL  "tcp://127.0.0.1:1883") ;; URL of the MQTT broker  
(def ^:const DB-READ-DELAY-MS 1000) ;; Delay in MS to read records

;; EmpId counter 
(def empId (atom 0))

;; Connect with MQTT broker 
(def mclient (mh/connect MQTT-BROKER-URL))

;;   
(defn read-table 
  "Function for testing, increment the counter"
  []
  (if (>= @empId 5)
    (do 
      (println "empId is done")
      nil)
    (do 
      (swap! empId inc)
      (str "Msg: " @empId " seconds"))))

;; 
(defn timerfn 
  "Async timer function to read employee table rows based on empId 
   and publish the on MQTT topic 'test' as a 'string' payload"
  [] 
  (reset! empId 1)
  (go-loop []
     (Thread/sleep DB-READ-DELAY-MS)
     (println "CALLING mysql/get-employee-by-id")
     (let [data (mysql/get-employee-by-id @empId)]
       (println "Data: " data)
       (if (empty? data)
          (do
            (println "No more records found...")
            (System/exit 0))
          (do
            (swap! empId inc)
            (mh/publish mclient "test" (str "" data))
            (recur))
       )
     )
    )
  )

;; --- Main ---
(defn -main
  "Clojure MQTT Publish Client"
  [& args]
  (println "Clojure MQTT Publish Client")
  (if (mh/connected? mclient)
    (do
      (println "MQTT client connected with broker.")
      (timerfn))
    (do
      (println "MQTT client Failed to connect with broker!")
      (System/exit 0)))  
  )
