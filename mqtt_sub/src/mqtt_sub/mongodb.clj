(ns mqtt-sub.mongodb
  (:gen-class)
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

;;(def db (atom nil))

#_(def conn (try (mg/connect {:host "localhost" :port 27018}) 
               (catch Exception e (println (str "caught exception in connection: " (.toString e))))) )

#_(def db (try (mg/get-db conn "usersdb") 
               (catch Exception e (println (str "caught exception in get-db: " (.toString e))))) )

(def conn (mg/connect {:host "localhost" :port 27017}))
(def db   (mg/get-db conn "usersdb"))

(defn add-test-user 
  "Function adds user with which database functionality is tested"
  [] 
   (mc/insert db "user" {:name "Johnny" :age 32}))

(defn add-user 
  "Function to write user data to mongodb 
   user-data - is received as string and converted to map using read-string"
  [user-data] 
  (try (mc/insert db "user" (read-string user-data))
       (catch Exception e (println (str "caught exception in insert: " (.toString e)))))
  )

#_(defn connect-mongo
  "Function adds user with which database functionality is tested"
  [host port]
  (def db (mg/get-db (mg/connect {:host host :port port}) "usersdb"))
  )

#_(defn add-test-user 
  "Function adds user with which database functionality is tested"
  [] 
  (let [conn (mg/connect)
        db   (mg/get-db conn "usersdb")]
    (mc/insert db "user" {:name "Johnny" :age 32})))

#_(defn add-user 
  "Function to write user data to mongodb 
   user-data - is received as string and converted to map using read-string"
  [user-data] 
  (if (= db nil)
    (do
      (println "Not connected!")
      (System/exit 0))
    (mc/insert db "user" (read-string user-data)))
  )


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (add-test-user)
  ;;(add-user (read-string "{:id 3, :name \"Prajakta\", :department \"IT\", :designation \"SE\"}"))
  (println "Hello, World!")
   )