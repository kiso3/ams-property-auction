(ns client-new
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button]]
        [databasebroker :only [get-client-by-id insert-new-client]]
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
  [name parent-name lastname email phone street number place]
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


(defn new-client
  "Adds new cient"
  [name parent-name lastname email phone street number place]  
  (let [client-error-message (check-client-data  name parent-name 
                                                lastname email phone
                                                street number place)]
    (if-not (string? client-error-message)
      (do 
        (insert-new-client (maping-client-form                 
                 nil name parent-name lastname email 
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
        ))))



(defn client-new-page
  "Create client "
  []  
  (get-template "Create add client page"
   [:div.content
    [:p.clienttitle "Enter information about new client!"]
    [:p.clienterror (session/flash-get :client-error)]
    (form-to [:post "/new-client"]
             [:div.newclient
              [:div
               (label {:class "clientlabel"} :name "Name ")
               (text-field :name (session/flash-get :name))]
              [:div
               (label {:class "clientlabel"} :parent-name "Parent name")
               (text-field :parent-name (session/flash-get :parent-name))]
              [:div
               (label {:class "clientlabel"} :lastname "Last name")
               (text-field :lastname (session/flash-get :lastname))]
              [:div
               (label {:class "clientlabel"} :email "Emai")
               (text-field :email (session/flash-get :email))]
              [:div
               (label {:class "clientlabel"} :lastname "Phone")
               (text-field :phone (session/flash-get :phone))]
              [:div
               (label {:class "clientlabel"} :lastname "Street")
               (text-field :street (session/flash-get :street))]
               [:div
               (label {:class "clientlabel"} :lastname "Number")
               (text-field :number (session/flash-get :number))]
              [:div
               (label {:class "clientlabel"} :lastname "Place ")
               (text-field :place (session/flash-get :place))]
              [:div
               (submit-button "Add client")]])]))
