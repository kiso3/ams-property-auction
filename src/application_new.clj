(ns application-new
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button check-box]]
        [databasebroker :only [get-client-by-id 
                               get-property-by-id
                               insert-new-application]]
        [maping-object :only [maping-application-form]]
        ))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))


(defn check-application-data
  "Checks data."
  [property-id client-id 
          informed-with-offering deposit
          date-of-visite-offering
          submission-date])


(defn application-insert
  "Add new application for cients"
  [property-id client-id 
          informed-with-offering deposit
          date-of-visite-offering
          submission-date]  
  (let [application-error-message (check-application-data property-id client-id 
                                                     informed-with-offering deposit
                                                     date-of-visite-offering
                                                     submission-date)]
    (if-not (string? application-error-message)
      (do 
        (println   (str property-id client-id 
          informed-with-offering deposit
          date-of-visite-offering
          submission-date) )
        (insert-new-application (maping-application-form                 
                             nil
                            (parse-int property-id) 
                            (parse-int client-id) 
                            (if(nil? informed-with-offering)
                              "No"
                              "Yes"
                              ) 
                            (if (nil? deposit)
                              "No"
                              "Yes"
                              )
                            (str date-of-visite-offering)
                            nil))
        (response/redirect (str "/applicationslist?id=" property-id))
      ))))

(defn new-application-page
  "Create application"
 [propert-id client-id] 
  (let [client (get-client-by-id  (parse-int client-id))
    property (get-property-by-id  (parse-int propert-id))]  
  (session/put! :client client)
  (session/put! :property property)    
  (get-template "Create edit client page"
   [:div.content
    [:p.clienttitle "Information about new client!"]
    [:p.clienterror (session/flash-get :client-error)]

    [:div.newclient
     [:div
      (label {:class "clientlabel"} :name "Name")
      (text-field {:readonly true}:name ((session/get :client):name))
      ]
     [:div
      (label {:class "clientlabel"} :parent-name "Parent name")
      (text-field {:readonly true} :parent-name ((session/get :client) :parent-name))]
     [:div
      (label {:class "clientlabel"} :lastname "Last name")
      (text-field {:readonly true} :lastname ((session/get :client) :lastname))]
     [:div
      (label {:class "clientlabel"} :email "Email")
      (text-field {:readonly true} :email ((session/get :client) :email))]
     [:div
      (label {:class "clientlabel"} :lastname "Phone")
      (text-field {:readonly true} :phone ((session/get :client) :phone))]
     [:div
      (label {:class "clientlabel"} :lastname "Street")
      (text-field {:readonly true} :street (get-in (session/get :client) [:address :street]))]
     [:div
      (label {:class "clientlabel"} :lastname "Number")
      (text-field {:readonly true} :number (get-in (session/get :client) [:address :number]))]
     [:div
      (label {:class "clientlabel"} :lastname "Place")
      (text-field {:readonly true} :place (get-in (session/get :client) [:address :place]))]
     [:p.applicationtitle "Information about new application!"]
     ]
    
    (form-to [:post "/application-new"] 
             [:div.newaoolication
              [:div               
               (text-field {:hidden true} :property-id ((session/get :property):_id))]
              [:div               
               (text-field {:hidden true} :client-id ((session/get :client):_id))]              
              [:div
               (label  {:class "check-box-label"} :informed-with-offering "Informed with offering")
               (check-box  "informed-with-offering" false "1") ]
              [:div
               (label {:class "check-box-label"} :deposit "Deposit")
               (check-box  "deposit" false "1") ]
               
              [:div               
               (label {:class "clientlabel"} :date-of-visite-offering "Visite date")
               (text-field {:readonly false} :date-of-visite-offering (session/flash-get :date-of-visite-offering))]
              [:div               
               (label {:class "clientlabel"} :submission-date "Submission date")
               (text-field {:readonly true} :submission-date (session/flash-get :submission-date))]
              [:div.content
               (submit-button  "Save applications" )]])])))