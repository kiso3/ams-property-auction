(ns client-not-have-appication-for-property 
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button]]
        [databasebroker :only [client-not-have-appication-for-property]]
        ))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn show-one-client 
  "Show data for one client "
  [client id]
  [:tr.contnt
   [:td.contnt (str (client :name) " " (client :lastname))]
   [:td.contnt(client :email)]
   [:td.contnt(client :phone)]
   [:td.contnt (str (get-in client [:address :place]) " " 
                    (get-in client [:address :street])"-"
                    (get-in client [:address :number]))]
   [:td.contnt (form-to [:get "/application-new"]
                        [:div (text-field {:hidden true} :property-id id )
                        [:div (text-field {:hidden true} :client-id (client :_id) )
                         [:div (submit-button "Add application")]]])
    (form-to [:post "/delete-client"]
             [:div (text-field {:hidden true} :id (client :_id) )
                ])]
   ])

(defn show-all-clients
  "Retrieves all client from database and displays them."
  [id]
  [:div.clientinfo
   (let [cients (client-not-have-appication-for-property (parse-int id))]
      [:div.contetn 
		   [:table
		    [:tr.contetn 
		     [:th.contnt "Clent"]
         [:th.contnt "Email"]
         [:th.contnt "Phone"]
         [:th.contnt "Address"]
         [:th.contnt ""]
	       (for [client cients]
			      (show-one-client client id))
       ]]])
   ])

(defn client-not-have-appication-for-property-page
  "Show all client not have submit aplication for propetry page"
  [id]
  (get-template "Client not have submit aplication for propetry page"
   [:div.content
    [:p.applicationlisttitle "List of client who not have submit application."]
    (show-all-clients id)]))