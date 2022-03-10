(ns mqtt-pub.mysqldb
  (:gen-class)
  (:require
    [clojure.java.jdbc :as jdbc]) )

;; Constants used
(def ^:const SQL-URL "//localhost:3306/company?useSSL=false&characterEncoding=utf8")
(def ^:const PROTOCOL "mysql")
(def ^:const USER "root")
(def ^:const PASSWORD "root")

;; Defines specifications for mysql connection
(def db-spec {:subprotocol PROTOCOL
              :subname SQL-URL
              :user USER
              :password PASSWORD})

#_(defn get-employee-by-id
  "Function returns record of specific employees existing in database."
  [id]
  (let [db-spec {:subprotocol PROTOCOL
                 :subname SQL-URL
                 :user USER
                 :password PASSWORD}]
    (str "" (first (jdbc/query db-spec
                               ["SELECT * FROM employee where id = ?" id])))))

(defn print-name 
  "Function returns record of all employees existing in database"
  [name] 
  (println "in function " name)
  )

#_(defn get-all-employees 
  "Function returns record of all employees existing in database"
  [] 
  (jdbc/query db-spec
              ["SELECT * FROM employee"]))

(defn get-employee-by-id 
  "Function returns record of specific employees existing in database"
  [id]
  (str "" (first (jdbc/query db-spec
              ["SELECT * FROM employee where id = ?" id]))))

#_(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World in mysqldb 1")
  (println "by ID: "  (get-employee-by-id "Prajakta"))
  (println "ALL: " (get-all-employees))
  (print-name "prajakta")
  (println "Hello, World in mysqldb 2"))