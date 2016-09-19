(ns application-list  
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button]]
        [databasebroker :only [get-applications-for-property]]
        ))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn show-one-application 
  "Show data for one client "
  [application property-id]
  [:tr.contnt
   [:td.contnt(application :submission-date)]
   [:td.contnt(application :informed-with-offering)]
   [:td.contnt(application :date-of-visite-offering)]
   [:td.contnt(application :deposit)]
   [:td.contnt(application :client-id)]
   [:td.contnt 
    (form-to [:get "/application-edit"]             
              (text-field {:hidden true} :application-id (application :_id) )              
              [:div (submit-button "Edit")])
    (form-to [:post "/delete-application"]
             (text-field {:hidden true} :application-id (application :_id) )
             (text-field {:hidden true} :property-id property-id )
             [:div (submit-button "Delete")])]
   ])

(defn show-all-applications
  "Retrieves all aplication for property from database and displays them."
  [property-id]  
  [:div.clientinfo
   (let [applications (get-applications-for-property (parse-int property-id))]     
  [:div.contetn 
		   [:table
		    [:tr.contetn 
		     [:th.contnt "Submission date"]
     [:th.contnt "Informed"]
     [:th.contnt "Date of visite"]
     [:th.contnt "Deposit"]
     [:th.contnt "Client id"]     
     [:th.contnt "Command"]
	   (for [application applications]
			 (show-one-application application property-id))
   ]]])
   [:div.addbutton
    (form-to [:get "/client-not-have-appication-for-property-page"]
             [:div (text-field {:hidden true} :id property-id )]
             [:div (submit-button "Add application")])]])

(defn all-application-for-propety-page
  "Show all application for property page"
  [property-id]
  (get-template "Submit aplications for propetry page"
   [:div.content
      [:p.applicationlisttitle "Liat of all application submit for property"]
    (show-all-applications property-id)]))