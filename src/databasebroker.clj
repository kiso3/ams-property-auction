(ns databasebroker  
  (:require [noir.session :as session]
            [clj-time.format :as time-format]
            [clj-time.core :as time])
  (:use [somnium.congomongo]
        [maping-object]))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))


(def conn
  (make-connection "property_auction"))

(set-connection! conn)

(defn- generate-id [collection]
  "Generate entity identifier." 
  (:seq (fetch-and-modify :sequences {:_id collection} {:$inc {:seq 1}}
                          :return-new? true :upsert? true)))

(defn- insert-entity [collection values]
   "Insert an entity into database."
  (insert! collection (assoc values :_id (generate-id collection))))

;client
(defn insert-new-client
  "Insert new client into datebase."
  [client]
  (insert-entity :client (maping-client client)))

(defn get-client-by-email [email]
  "Find client by email."
  (fetch-one :client :where {:email email}))

(defn get-client-by-id [id]
  "Find client by id."
  (fetch-one :client :where {:_id id} ))

(defn client-not-have-appication-for-property [id]
  "Find all client not have submit aplication for propetry."
 (let [applications (fetch :application :where {:property-id id} )
	clients (fetch :client)]
	(let [del-client(for [application applications]
	                                           (application :client-id))]
	(remove (fn[x](contains? (set del-client) (x :_id))) clients))))

(defn get-all-clients []
  "Get all client"
  (fetch :client))

(defn remove-client-by-id [id]
  "Remove client by id. "
  (somnium.congomongo/destroy! :client {:_id id}))

(defn update-client
  "Update client into datebase."
  [client]
  (update! :client {:_id (client :_id)} 
             (maping-client client)))

;property
(defn insert-new-property
  "Insert data fo new nadmetnaje into datebase."
  [property]
  (insert-entity :property (maping-property property)))

(defn remove-property-by-id [id]
  "Remove property by id."
  (somnium.congomongo/destroy! :application  {:property-id id})
  (somnium.congomongo/destroy! :property  {:_id id}))

(defn get-property-by-id [id]
  "Find property by id."
  (fetch-one :property :where {:_id id}))

(defn get-property-by-mark [mark]
  "Find property by mark."
  (fetch-one :property :where {:mark mark}))

(defn get-all-propertys []
  "Get all property"
  (fetch :property))

;application
(defn insert-new-application
  "Insert new application into datebase."
  [application]
(insert-entity :application (maping-application application)))


(defn get-application-by-id [id]
  "Find application by id."  
  (let [application (fetch-one :application :where {:_id id})]
  (maping-application2 
    application
    (get-client-by-id (application :client-id))
    (get-property-by-id (application :property-id)))))

(defn get-applications-for-property [id]
  "Find all aplisations for property."
  (fetch :application :where {:property-id id}))


(defn remove-application-by-id [id]
  "Remove application by id."
  (somnium.congomongo/destroy! :application {:_id id}))

(defn update-application
  "Update application into datebase."
  [application]  
  (update! :application {:_id (application :_id)} 
             (maping-application application)))