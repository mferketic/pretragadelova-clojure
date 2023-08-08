(ns carservice.routes.home
  (:require
   [carservice.layout :as layout]
   [carservice.db.core :as db]
   [clojure.java.io :as io]
   [carservice.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

; --- PAGE CONFIGURATION --------------------------------------------------------

(defn home-page [request]
  (layout/render request "home.html"))

(defn about-page [request]
  (layout/render request "about.html"))

(defn bigService-page [{:keys [flash] :as request}]
  (layout/render
    request
    "search/bigService.html"
    (merge {:models (db/get-models)}
           {:types (db/get-types-by-service-id {:idService 1})}
    (select-keys flash [:year :errors]))))

(defn smallService-page [request]
  (layout/render request "smallService.html"))

(defn sensors-page [request]
  (layout/render request "sensors.html"))

(defn allParts-page [request]
  (layout/render request "about.html"))

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


; --- VALIDATION -----------------------------------------------------------------


; --- METHODE ---------------------------------------------------------------------


; --- ROUTING --------------------------------------------------------------------------

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}


   ["/" {:get home-page}]
   ["/about" {:get about-page}]

   ["/majorService" {:get bigService-page}]
   ["/minorService" {:get smallService-page}]
   ["/sensors" {:get sensors-page}]

   ["/allParts" {:get allParts-page}]
   ["/deletePart" {:get deletePart-page}]
   ["/addPart" {:get addPart-page}]
   ["/updatePart" {:get updatePart-page}]


   ["/cart" {:get cart-page}]
   ["/addToCart" {:get addToCart-page}]
   ["/deleteFromCart" {:get deleteFromCart-page}]

   ])
