(ns template
  (:require [noir.session :as session])
  (:use  [hiccup.core :only [html]]
         [hiccup.page :only [include-css doctype]]))

(defn menu
  "Generates the menu for all pages when the user is logged in."
  []
  [:ul#menu 
   [:li#home
    [:a {:href "/"} "Home"]]
   [:li#cientlist
    [:a {:href "/clientlist"} "All clients"]]
   [:li#propertyslist
    [:a {:href "/propertyslist"} "All propertys"]]
   ])


(defn get-template
  "Generates template for all pages in application."
  [title content]
  (html
    (doctype :xhtml-transitional)
    [:html {:xmlns "http://www.w3.org/1999/xhtml" "xml:lang" "en" :lang "en"} 
      [:head
        (include-css "/css/style.css")
        [:meta {:charset "UTF-8"}]
        [:title title]]
      [:body
        (menu)
         [:div#container
          [:div#titlemain
	          [:h1#title "Property auction"]
           ]
          [:div
          content]]
         [:div#footer "Mladen Kiso 2016, FON"]]]))
