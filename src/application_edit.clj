(ns application-edit
    (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button check-box]]
        [databasebroker :only [get-client-by-id 
                               get-property-by-id
                               get-application-by-id
                               update-application]]
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


(defn application-edit
  "Edit application for cients "
  [application-id  
  property-id
   client-id
  informed-with-offering 
  deposit date-of-visite-offering submission-date]  
 
  (let [application-error-message (check-application-data property-id client-id 
                                                     informed-with-offering deposit
                                                     date-of-visite-offering
                                                     submission-date)]
    (println (str   application-id  property-id
   client-id informed-with-offering  deposit 
  date-of-visite-offering submission-date ))
    
   (if-not (string? application-error-message)
      (do 
        (update-application (maping-application-form              
                             (parse-int application-id)       
                            (parse-int property-id)
                             (parse-int client-id)
                             informed-with-offering 
                             deposit
                             date-of-visite-offering
                             nil))
        (response/redirect (str "/applicationslist?id=" property-id))
      ))))

(defn edit-application-page
  "Edit application"
 [application-id] 
  (let [application (get-application-by-id  (parse-int application-id))]  
  (session/put! :application application)  
  (get-template "Edit appliation page"
   [:div.content
    [:p.pagetitle "Information about client!"]
    [:p.error (session/flash-get :client-error)]
    [:div.editclient
     [:div
      (label {:class "clientlabel"} :name "Name")
      (text-field {:readonly true}:name (get-in (session/get :application) [:client :name]))
      ]
     [:div
      (label {:class "clientlabel"} :parent-name "Parent name")
      (text-field {:readonly true} 
                  :parent-name (get-in 
                                 (session/get :application) 
                                 [:client :parent-name]))]
     [:div
      (label {:class "clientlabel"} :lastname "Last name")
      (text-field {:readonly true} 
                  :lastname (get-in 
                              (session/get :application) 
                              [:client :lastname]))]
     [:div
      (label {:class "clientlabel"} :email "Email")
      (text-field {:readonly true} 
                  :email (get-in 
                           (session/get :application) 
                           [:client :email]))]
     [:div
      (label {:class "clientlabel"} :lastname "Phone")
      (text-field {:readonly true} 
                  :phone (get-in 
                           (session/get :application) 
                           [:client :phone]))]
     [:div
      (label {:class "clientlabel"} :lastname "Street")
      (text-field {:readonly true} 
                  :street (get-in 
                            (session/get :application) 
                            [:client :address :street]))]
     [:div
      (label {:class "clientlabel"} :lastname "Number")
      (text-field {:readonly true} 
                  :number (get-in 
                            (session/get :application) 
                            [:client :address :number]))]
     [:div
      (label {:class "clientlabel"} :lastname "Place")
      (text-field {:readonly true} 
                  :place (get-in 
                           (session/get :application) 
                           [:client :address :place]))]
     [:p.applicationtitle "Information about new application! "]
     ]

    (form-to [:post "/application-edit"] 
             [:div.editapplication
              [:div               
	               (text-field {:hidden true} :application-id (get-in (session/get :application) [:_id]))]
              [:div               
               (text-field {:hidden true} :property-id (get-in (session/get :application) [:property-id]))]
              [:div               
               (text-field {:hidden true} :client-id (get-in (session/get :application) [:client-id]))]
              [:div
               (label  {:class "check-box-label"} :informed-with-offering "Informed with offering")
               (check-box  "informed-with-offering" (let [is-check (get-in (session/get :application) [:informed-with-offering])]
                                                      (if (= is-check "Yes")
                                                        true
                                                        false)) "Yes")
               ]
              [:div
               (label {:class "check-box-label"} :deposit "Deposit")
               (check-box  "deposit" (let [is-check (get-in (session/get :application) [:deposit])]
                                       (if (= is-check "Yes")
                                         true
                                         false))  "Yes") 
               ]
              [:div               
               (label {:class "clientlabel"} :date-of-visite-offering "Visite date")
               (text-field {:readonly false} :date-of-visite-offering (str (get-in (session/get :application) [:date-of-visite-offering])))]
              [:div               
               (label {:class "clientlabel"} :submission-date "Submission date")
               (text-field {:readonly true} :submission-date (str (get-in (session/get :application) [:submission-date])))]
              [:div.content
               (submit-button  "Edit applications" )]])])))