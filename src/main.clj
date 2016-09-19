(ns main
  (:use  [template :only [get-template]]
         ))

(defn main-page 
  "Displays main page."
  []
  (get-template "Property auction" 
   [:div.content
    [:img {:class "main-page-image" :src "../images/main-page-image.jpg"}]
    ]))

