(ns maping-object
    (:require 
            [clj-time.format :as time-format]
            [clj-time.core :as time]))

(def parser-formatter (time-format/formatter "yyyy-MM-dd HH:mm:ss"))

(defn maping-client-form
  "maping client"
  [id name parent-name lastname email phone street number place]  
	 {         
    :_id id
	  :name name
	  :parent-name parent-name
	  :lastname lastname
	  :email email
	  :phone phone
	  :address {
			:street street
			:number number
			:place place
			}
	  }
  )

(defn maping-client
  "maping client"
  [client]
	 {         
    :_id (client :_id)
	  :name (client  :name)
	  :parent-name (client  :parent-name)
	  :lastname (client  :lastname)
	  :email (client  :email)
	  :phone (client  :phone)
	  :address {
			:street (get-in client [:address :street] )
			:number (get-in client  [:address :number])
			:place (get-in client [:address :place] )
			}
	  })


(defn maping-property
  "maping property"
  [property]
  {
    :_id (property :_id )
		:mark (property :mark )
		:start-price (property  :start-price)
		:deposit (property  :deposit)
		:area (property  :area)                    
		:advertisement {
			:municipality-name (get-in property [:advertisement :municipality-name] )
			:licitaition-step (get-in property  [:advertisement :licitaition-step])
			:max-area (get-in property [:advertisement :max-area] )
	  }
  })


(defn maping-application
  "maping application"
  [application]
  {
    :_id (application :_id)
		:submission-date (time-format/unparse parser-formatter (time/now))
		:informed-with-offering(application  :informed-with-offering)
		:date-of-visite-offering (application  :date-of-visite-offering)
		:deposit (application  :deposit)                                       
   :property-id (get-in application [:property-id] )                    
   :client-id (get-in application [:client-id])
   })

(defn maping-application2
  "maping application"
  [application client property]
  {
    :_id (application :_id)
		:submission-date (application :submission-date )
		:informed-with-offering(application  :informed-with-offering)
		:date-of-visite-offering (application  :date-of-visite-offering)
		:deposit (application  :deposit)                                       
		:property (maping-client property)                    
	  :client (maping-client client)
   :property-id (get-in application [:property-id] )                    
   :client-id (get-in application [:client-id])
   })


(defn maping-application-form
  "maping application form "
  [id property-id client-id informed-with-offering deposit
   date-of-visite-offering submission-date]
  {
    :_id id
		:submission-date submission-date
		:informed-with-offering informed-with-offering
		:date-of-visite-offering date-of-visite-offering
		:deposit deposit                                       
		:property-id property-id                    
	  :client-id client-id
   })

