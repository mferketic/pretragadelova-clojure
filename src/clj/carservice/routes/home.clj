(ns carservice.routes.home
  (:require
   [carservice.layout :as layout]
   [carservice.db.core :as db]
   [clojure.java.io :as io]
   [struct.core :as st]
   [carservice.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

; --- PAGE CONFIGURATION --------------------------------------------------------

(defn home-page [request]
  (layout/render request "home.html"))

(defn about-page [request]
  (layout/render request "about.html"))

(defn part-page [request]
  (let [parts (:parts request)]
    (println "Parts data:" parts)
    (layout/render request "parts.html" {:data {:parts parts}})))



(defn bigService-page [request]
  (let [{:keys [flash parts] :as request} request]
    (layout/render
      request
      "search/bigService.html"
      (merge {:models (db/get-models)}
             {:types (db/get-types-by-service-id {:idService 1})}
             ;PART THAT DOESN'T WORK
             ; {:parts parts}
             {:parts (db/get-my-parts {:model1 1, :type1 0, :year1 2010})}
             (println "Parts data:" parts)
             (select-keys flash [:model1 :type1 :year1 :errors])))))




(defn smallService-page [request]
  (layout/render request "smallService.html"))

(defn sensors-page [request]
  (layout/render request "sensors.html"))

(defn allParts-page [request]
  (layout/render
    request
    "parts/allParts.html"
    {:parts (db/get-parts)}))

(defn deletePart-page [request]
  (layout/render request "about.html"))

(defn addPart-page [request]
  (layout/render request "about.html"))

(defn updatePart-page [request]
  (layout/render request "about.html"))

(defn cart-page [request]
  (layout/render request "about.html"))

(defn addToCart-page [request]
  (layout/render request "about.html"))

(defn deleteFromCart-page [request]
  (layout/render request "about.html"))

; --- SCHEMA ----------------------------------------------------------------------

;(Searching for types)
(def search-schema
  [[:model1
    st/required]
   [:type1
     st/required]
  [:year1
    st/required
    {:message "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]])

;(Add part to cart)
(def idCheck-schema
  [[:id
    st/required]
])

; --- VALIDATION -----------------------------------------------------------------

;Search part
(defn validate-search [params]
  (first (st/validate params search-schema)))

;IdCheck
(defn validate-id [params]
  (first (st/validate params idCheck-schema)))

; --- METHODE ---------------------------------------------------------------------

;SEARCH MAJOR SERVICE PART
(defn search-parts [{:keys [params]}]
  (let [errors (validate-search params)]
    (if (seq errors)
      (-> (response/found "/majorService")
          (assoc :flash (assoc params :errors errors)))
      (let [params-no-token (dissoc params :__anti-forgery-token)
            parts (db/get-my-parts params-no-token)]
        (-> (response/found "/majorService")
            (assoc :parts parts)
            (assoc :flash (assoc params :errors {})))))))

;ADD PART TO CART
(defn add-to-cart [{:keys [params]}]
  (let [errors (validate-id params)]
    (if errors
      (-> (response/found "/majorService")
          (assoc :flash (assoc params :errors errors)))
      (let [part (db/get-part-by-id {:id (:id params)})]
        (if-not part
          (-> (response/found "/majorService")
              (assoc :flash (assoc params :errors {:id "Part with ID doesnt exist!!"})))
          (do
            (db/add-to-cart! {:idUser 1 :idPart (:id params)})
            (response/found "/cart")))))))


; --- ROUTING --------------------------------------------------------------------------

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}

   ;API
   ["/searchForBigService" {:post search-parts}]
   ["/addToCart" {:post add-to-cart}]

   ;Routes
   ["/" {:get home-page}]
   ["/about" {:get about-page}]

   ["/majorService" {:get bigService-page}]
   ["/minorService" {:get smallService-page}]
   ["/sensors" {:get sensors-page}]

   ["/allParts" {:get allParts-page}]
   ["/deletePart" {:get deletePart-page}]
   ["/addPart" {:get addPart-page}]
   ["/updatePart" {:get updatePart-page}]


   ["/parts" {:get part-page}]
   ["/cart" {:get cart-page}]
   ["/deleteFromCart" {:get deleteFromCart-page}]

   ])
