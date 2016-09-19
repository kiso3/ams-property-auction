(ns property-list  
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button]]
        [databasebroker :only [get-all-propertys]]
        ))


(defn show-one-propetry
  "Show data for one propetry"
  [propetry]
  [:tr.contnt
   [:td.contnt (str (propetry :mark))]
   [:td.contnt(propetry :start-price)]
   [:td.contnt(propetry :deposit)]
   [:td.contnt(propetry :area)]
   [:td.contnt(get-in propetry [:advertisement :municipality-name])]
   [:td.contnt (form-to [:get "/applicationslist"]
             [:div (text-field {:hidden true} :id (propetry :_id) )
              [:div (submit-button "Show application")]])]
   ])

(defn show-all-propetrys
  "Retrieves all propetrys from database and displays them."
  []
  [:div.clientinfo
   (let [propertys  (get-all-propertys)]
      [:div.contetn 
		   [:table
		    [:tr.contetn 
		     [:th.contnt "Mark"]
         [:th.contnt "Start price"]
         [:th.contnt "Deposit"]
         [:th.contnt "Area"]         
         [:th.contnt "Municipality"]
         [:th "Command"]
	       (for [propetry propertys]
			      (show-one-propetry propetry))
       ]]])
   ])

(defn all-property-page
  "Show all propetry page"
  []
  (get-template "All propetry page"
   [:div.content
    (show-all-propetrys)]))