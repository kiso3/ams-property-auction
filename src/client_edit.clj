(ns client-edit
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button]]
        [databasebroker :only [get-client-by-id update-client]]
        [maping-object :only [maping-client-form]]
        ))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn validate-email 
  [email]
  (let [pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"]
    (and (string? email) (re-matches pattern email))))

(defn check-client-data
  "Checks client data."
  [id name parent-name lastname email phone street number place]
  (cond
    (> 2 (.length name)) "Client name must be at least 2 character long. "
    (> 2 (.length parent-name)) "Client parent name must be at least 2 character long."
    (> 2 (.length lastname)) "Client lastname must be at least 2 character long."
    (nil? (validate-email email)) "Email address is not valid."
    (> 9 (.length phone)) "Client phone must be at least 9 characters long."
    (> 5 (.length street)) "Client street  must be at least 5 characters long."
    (> 1 (.length number)) "Client street number  must be at least 1 characters long."
    (> 2 (.length place)) "Client  place must be at least 2 characters long."
    :else true))

(defn edit-client
  "Update cient "
  [id name parent-name lastname email phone street number place]  
  (let [client-error-message (check-client-data id name parent-name 
                                                lastname email phone
                                                street number place)]
    (if-not (string? client-error-message)
      (do 
        (update-client (maping-client-form                 
                 (parse-int id) name parent-name lastname email 
                 phone street number place ))
        (response/redirect "/clientlist"))
      (do
        (session/flash-put! :client-error client-error-message)
        (session/flash-put! :name name)
        (session/flash-put! :parent-name parent-name)
        (session/flash-put! :lastname lastname)
        (session/flash-put! :email email)
        (session/flash-put! :phone phone)
        (session/flash-put! :street street)
        (session/flash-put! :number number)
        (session/flash-put! :place place)
        (response/redirect (str "/edit-client?id=" id ))))))


(defn client-edit-page
  "Eedit client"
  [id]   
  (let [client (get-client-by-id  id)]  
  (session/put! :client client)    
  (get-template "Create edit client page"
   [:div.content
    [:p.clienttitle "Enter information about new client!"]
    [:p.clienterror (session/flash-get :client-error)]
    (form-to [:post "/edit-client"]
             [:div.newclient
              [:div               
               (text-field {:hidden true} :id ((session/get :client):_id))
               ]
              [:div
               (label {:class "clientlabel"} :name "Name ")
               (text-field :name ((session/get :client):name))
               ]
              [:div
               (label {:class "clientlabel"} :parent-name "Parent name")
               (text-field :parent-name ((session/get :client) :parent-name))]
              [:div
               (label {:class "clientlabel"} :lastname "Last name")
               (text-field :lastname ((session/get :client) :lastname))]
              [:div
               (label {:class "clientlabel"} :email "Emai")
               (text-field :email ((session/get :client) :email))]
              [:div
               (label {:class "clientlabel"} :lastname "Phone")
               (text-field :phone ((session/get :client) :phone))]
              [:div
               (label {:class "clientlabel"} :lastname "Street")
               (text-field :street (get-in (session/get :client) [:address :street]))]
               [:div
               (label {:class "clientlabel"} :lastname "Number")
               (text-field :number (get-in (session/get :client) [:address :number]))]
              [:div
               (label {:class "clientlabel"} :lastname "Place")
               (text-field :place (get-in (session/get :client) [:address :place]))]
              [:div
               (submit-button "Save change")]])])))