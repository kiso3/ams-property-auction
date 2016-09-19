(ns client-list  
  (:require [noir.session :as session]
            [ring.util.response :as response])
  (:use [template :only [get-template]]
        [hiccup.form :only [form-to label text-field submit-button]]
        [databasebroker :only [get-all-clients]]
        ))


(defn show-one-client 
  "Show data for one client"
  [client]
  [:tr.contnt
   [:td.contnt (str (client :name) " " (client :lastname))]
   [:td.contnt(client :email)]
   [:td.contnt(client :phone)]
   [:td.contnt (str (get-in client [:address :place]) " " 
                    (get-in client [:address :street])"-"
                    (get-in client [:address :number]))]
   [:td.contnt (form-to [:get "/edit-client"]
             [:div (text-field {:hidden true} :id (client :_id) )
              [:div (submit-button "Edit")]])
						    (form-to [:post "/delete-client"]
						       [:div (text-field {:hidden true} :id (client :_id) )
						        [:div (submit-button "Delete")]])]
   ])

(defn show-all-clients
  "Retrieves all client from database and displays them."
  []
  [:div.clientinfo
   (let [cients (get-all-clients)]
      [:div.contetn 
		   [:table
		    [:tr.contetn 
		     [:th.contnt "Clent"]
         [:th.contnt "Email"]
         [:th.contnt "Phone"]
         [:th.contnt "Address"]
         [:th.contnt "Command"]
	       (for [client cients]
			      (show-one-client client))
       ]]])
   [:div.addbutton
    (form-to [:get "/new-client"]
              [:div (submit-button "Add client")] )]])

(defn all-client
  "Show all client page"
  []
  (get-template "All client page"
   [:div.content
        [:p.clientlisttitle "List of all client!"]
    (show-all-clients)]))