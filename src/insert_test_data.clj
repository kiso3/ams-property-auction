(ns insert-test-data
  (:require 
            [clj-time.format :as time-format]
            [clj-time.core :as time])
  (:use 
        [databasebroker :only [insert-new-property insert-new-client
                               insert-new-application 
                               get-property-by-mark
                               get-client-by-email]]
        ))

 (def parser-formatter (time-format/formatter "yyyy-MM-dd HH:mm:ss"))

(defn insert-test-data []        
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
 
  (insert-new-client {
                      :name "Milan"
                      :parent-name "Nenad"
                      :lastname "Petrović"
                      :email "mialn@email.com"
                      :phone "+38164111114"
                      :address {
                               :street "Vojvođanska"
                               :number "107"
                               :place "Bački Petrovac"
                               } 
                      })

 (insert-new-application {                              
															:submission-date (time-format/unparse parser-formatter (time/now))
															:informed-with-offering "Yes"
															:date-of-visite-offering "2016-02-01"
															:deposit "Yes"                                       
															:property-id ((get-property-by-mark "BP-2016-01") :_id)                    
														  :client-id ((get-client-by-email "pera@peric.com") :_id)
                          })
 (insert-new-application {                              
															:submission-date (time-format/unparse parser-formatter (time/now))
															:informed-with-offering "Yes"
															:date-of-visite-offering "2016-02-01"
															:deposit "Yes"                                       
															:property-id ((get-property-by-mark "BP-2016-01") :_id)                    
														  :client-id ((get-client-by-email "milos@email.com") :_id)
                          })
 
 (insert-new-application {                              
															:submission-date (time-format/unparse parser-formatter (time/now))
															:informed-with-offering "Yes"
															:date-of-visite-offering "2016-02-01"
															:deposit "Yes"                                       
															:property-id ((get-property-by-mark "BP-2016-01") :_id)                    
														  :client-id ((get-client-by-email "dusan@email.com") :_id)
                          })
 
  (insert-new-application {                              
															:submission-date (time-format/unparse parser-formatter (time/now))
															:informed-with-offering "Yes"
															:date-of-visite-offering "2016-02-02"
															:deposit "Yes"                                       
															:property-id ((get-property-by-mark "BP-2016-02") :_id)                    
														  :client-id ((get-client-by-email "milos@email.com") :_id)
                          })
  
   (insert-new-application {                              
															:submission-date (time-format/unparse parser-formatter (time/now))
															:informed-with-offering "Yes"
															:date-of-visite-offering "2016-02-02"
															:deposit "Yes"                                       
															:property-id ((get-property-by-mark "BP-2016-02") :_id)                    
														  :client-id ((get-client-by-email "dusan@email.com") :_id)
                          })
   
    (insert-new-application {                              
															:submission-date (time-format/unparse parser-formatter (time/now))
															:informed-with-offering "Yes"
															:date-of-visite-offering "2016-02-03"
															:deposit "Yes"                                       
															:property-id ((get-property-by-mark "BP-2016-03") :_id)                    
														  :client-id ((get-client-by-email "dusan@email.com") :_id)
                          })
    
 )