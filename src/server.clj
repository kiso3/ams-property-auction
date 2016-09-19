(ns server
  (:require [compojure.route :as route]
            [noir.session :as session]
				    [ring.util.response :as response])
  (:use [compojure.core :only [defroutes GET POST DELETE PUT]]
        [ring.adapter.jetty :only [run-jetty]]
        [main :only [main-page]]
        [ring.middleware.reload :only [wrap-reload]]
        [ring.middleware.stacktrace :only [wrap-stacktrace]]
        [ring.middleware.params :only [wrap-params]]
        [databasebroker :only [insert-new-property insert-new-client 
                               remove-client-by-id update-client
                               remove-application-by-id]]
        [client-list :only [all-client]]
        [client-new :only [client-new-page new-client]]
        [client-edit :only [client-edit-page edit-client]]
        [maping-object :only [maping-client-form]]
        [property-list :only [all-property-page]]
        [application-list  :only [all-application-for-propety-page]]
        [client-not-have-appication-for-property :only [client-not-have-appication-for-property-page]]
        [application-new :only [new-application-page application-insert]]
        [application-edit :only [edit-application-page application-edit]]
        ))


(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defroutes handler
  (GET "/" [] (main-page)) 
  (GET "/clientlist" [] (all-client))  
  (GET "/edit-client" [id] (client-edit-page (parse-int id)))
  (POST "/edit-client" [id name parent-name lastname email phone street number place]
       (edit-client id name parent-name lastname email phone street number place)) 
  (GET "/new-client" [] (client-new-page))
  (POST "/new-client" [name parent-name lastname email phone street number place] 
        (new-client name parent-name lastname email phone street number place))
  (POST "/delete-client" [id] (do
                                (remove-client-by-id (parse-int id ))
                                (response/redirect "/clientlist")))
  (GET "/propertyslist" [] (all-property-page))
  (GET "/applicationslist" [id] (all-application-for-propety-page id))
  (GET "/client-not-have-appication-for-property-page" [id] (client-not-have-appication-for-property-page id))  
  (GET "/application-new" [property-id client-id] (new-application-page property-id client-id ))
  (POST "/application-new" [property-id client-id  informed-with-offering deposit
                            date-of-visite-offering submission-date] 
        (application-insert property-id client-id informed-with-offering 
                            deposit date-of-visite-offering submission-date))
  (POST "/delete-application" [application-id property-id] 
        (do
          (remove-application-by-id (parse-int application-id ))
          (response/redirect (str "/applicationslist?id=" property-id))))
  (GET "/application-edit" [application-id] (edit-application-page application-id ))
  (POST "/application-edit" [application-id property-id client-id
                             informed-with-offering deposit
                             date-of-visite-offering submission-date] 
        (application-edit application-id property-id client-id
                          informed-with-offering deposit date-of-visite-offering submission-date))
  (route/resources "/")
  (route/not-found "Page not found."))

 (def app
  (-> #'handler
    ;(wrap-keyword-params)
    ;(wrap-json-params)
    (wrap-reload)
    (wrap-params)
    (session/wrap-noir-flash)
    (session/wrap-noir-session)
    (wrap-stacktrace)))


 (defn start-jetty-server []
   (run-jetty #'app {:port 8081 :join? false})
   (println "\nProperty auction application. Browse to http://localhost:8081 in your browser to get started!"))
 
 
 (defn insert-test-data []    
     (do
     (insert-new-property   {			    
                             :mark "BP-2016-01"
                             :start-price 50000
                             :deposit 5000
                             :area 40                    
                             :advertisement {
                                             :municipality-name "Bački Petrovac"
                                             :licitaition-step 1000
                                             :max-area 1000
                                             }
                             })
     (insert-new-property   {
                            :mark "BP-2016-02"
                             :start-price 60000
                             :deposit 6000
                             :area 50                    
                             :advertisement {
                                             :municipality-name "Bački Petrovac"
                                             :licitaition-step 1000
                                             :max-area 1000
                                             }
                            })
     (insert-new-property   {			    
                            :mark "BP-2016-03"
                             :start-price 90000
                             :deposit 9000
                             :area 80                    
                             :advertisement {
                                             :municipality-name "Bački Petrovac"
                                             :licitaition-step 1000
                                             :max-area 1000
                                             }
                             })
    (insert-new-client {
                         :name "Pera"
                         :parent-name "Petar"
                         :lastname "Perić"
                         :email "pera@peric.com"
                         :phone "+38164111111"
                         :address {
                                  :street "Petrovića"
                                  :number "17"
                                  :place "Bački Petrovac"
                                  } 
                        })
     (insert-new-client {
                     :name "Miloš"
                     :parent-name "Nikola"
                     :lastname "Nikolić"
                     :email "milos@email.com"
                     :phone "+38164111112"
                     :address {
                              :street "Vojvođanska"
                              :number "234"
                              :place "Bački Petrovac"
                              } 
                     })
       (insert-new-client {
                            :name "Dušan"
                            :parent-name "Nemanja"
                            :lastname "Đorđević"
                            :email "dusan@email.com"
                            :phone "+38164111113"
                            :address {
                                     :street "Vojvođanska"
                                     :number "117"
                                     :place "Bački Petrovac"
                                     } 
                            })
       ))

 (defn -main [& args]
   (do
     (start-jetty-server)      
 ))
 