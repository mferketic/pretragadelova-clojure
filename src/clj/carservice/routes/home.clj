(ns carservice.routes.home
  (:require
    [carservice.layout :as layout]
    [carservice.db.core :as db]
    [buddy.hashers :as hashers]
    [next.jdbc :as jdbc]
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

(defn bigService-page [request]
  (let [{:keys [flash parts] :as request} request]
    (layout/render
      request
      "search/bigService.html"
      (merge {:models (db/get-models)}
             {:types (db/get-types-by-service-id {:idService 1})}
             ;PART THAT DOESN'T WORK ------------------------------------------------
             ; {:parts parts}
             (println "Parts data:" parts)
             {:parts (db/get-my-parts {:model1 1, :type1 0, :year1 2010})}

             (select-keys flash [:model1 :type1 :year1 :errors])))))

(defn smallService-page [request]
  (let [{:keys [flash] :as request} request]
  (layout/render
    request
    "search/smallService.html"
    (merge {:models (db/get-models)}
           {:types (db/get-types-by-service-id {:idService 2})}
           {:parts (db/get-my-parts {:model1 2, :type1 5, :year1 2007})}
           (select-keys flash [:model1 :type1 :year1 :errors])))))

(defn sensors-page [request]
  (let [{:keys [flash] :as request} request]
    (layout/render
      request
      "search/sensors.html"
      (merge {:models (db/get-models)}
             {:types (db/get-types-by-service-id {:idService 3})}
             {:parts (db/get-my-parts {:model1 1, :type1 10, :year1 2016})}
             (select-keys flash [:model1 :type1 :year1 :errors])))))

(defn allParts-page [request]
  (layout/render
    request
    "parts/allParts.html"
    {:parts (db/get-parts)}))

(defn deletePart-page [{:keys [flash] :as request}]
  (layout/render
    request
    "parts/deletePart.html"
  (select-keys flash [:id :errors])))

(defn addPart-page [{:keys [flash] :as request}]
  (layout/render
    request
    "parts/createPart.html"
    (merge {:models (db/get-models)}
           {:types (db/get-types)}
           (select-keys flash [:type1 :model1 :partName :serial :fromYear :toYear :price :description :errors]))))

(defn updatePart-page [{:keys [flash] :as request}]
  (layout/render
    request
    "parts/updatePart.html"
    (merge {:models (db/get-models)}
           {:types (db/get-types)}
           (select-keys flash [:type1 :model1 :partName :serial :fromYear :toYear :price :description :errors]))))

(defn buyPart-page [{:keys [flash] :as request}]
  (layout/render
    request
    "orders/buyPart.html"
    (merge {:parts (db/get-parts)}
           (select-keys flash [:city :email :phone :idPart :quantity :errors]))))

(defn orders-page [{:keys [flash] :as request}]
  (layout/render
    request
    "orders/orders.html"
    (merge {:orders (db/get-unsent-orders)}
           (select-keys flash [:id :errors]))))

(defn sentOrders-page [request]
  (layout/render
    request
    "orders/sentOrders.html"
    {:orders (db/get-sent-orders)}))

(defn login-page [request]
  (layout/render request "auth/login.html"))

; --- SCHEMA ----------------------------------------------------------------------

;(Searching for types)
(def search-schema
  [[:model1
    st/required]
   [:type1
    st/required]
   [:year1
    st/required
    {:message  "Year must be in valid format!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]])

(def addPart-schema
  [[:type1
    st/required]
   [:model1
    st/required]
   [:partName
    st/required
    st/string]
   [:serial
    st/required]
   [:fromYear
    st/required
    {:message  "Year must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:toYear
    st/required
    {:message  "Year must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:price
    st/required
    {:message  "Price must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:description
    st/required
    st/string]])

(def updatePart-schema
  [[:idPart
    st/required
    {:message  "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:type1
    st/required]
   [:model1
    st/required]
   [:partName
    st/required
    st/string]
   [:serial
    st/required]
   [:fromYear
    st/required
    {:message  "Year must be positive number!"
     :validate  (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:toYear
    st/required
    {:message  "Year must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:price
    st/required
    {:message  "Price must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:description
    st/required
    st/string]])

(def onlyId-schema
  [[:id
    st/required
    {:message  "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]])

(def buyPart-schema
  [[:city
    st/required
    st/string]
   [:email
    st/required]
   [:phone
    st/required]
  [:idPart
    st/required]
   [:quantity
    st/required
    {:message  "Quantity must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
])


; --- VALIDATION -----------------------------------------------------------------

;Search part
(defn validate-search [params]
  (first (st/validate params search-schema)))

;Add part
(defn validate-addPart [params]
  (first (st/validate params addPart-schema)))

;Update part
(defn validate-updatePart [params]
  (first (st/validate params updatePart-schema)))

;Delete part
(defn validate-onlyId [params]
  (first (st/validate params onlyId-schema)))

;Buy part
(defn validate-buyPart [params]
  (first (st/validate params buyPart-schema)))



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

;SEARCH MINOR SERVICE PART
(defn search-minor [{:keys [params]}]
  (let [errors (validate-search params)]
    (if (seq errors)
      (-> (response/found "/minorService")
          (assoc :flash (assoc params :errors errors)))
      (let [params-no-token (dissoc params :__anti-forgery-token)
            parts (db/get-my-parts params-no-token)]
        (-> (response/found "/minorService")
            (assoc :parts parts)
            (assoc :flash (assoc params :errors {})))))))

(defn search-sensor [{:keys [params]}]
  (let [errors (validate-search params)]
    (if (seq errors)
      (-> (response/found "/sensors")
          (assoc :flash (assoc params :errors errors)))
      (let [params-no-token (dissoc params :__anti-forgery-token)
            parts (db/get-my-parts params-no-token)]
        (-> (response/found "/sensors")
            (assoc :parts parts)
            (assoc :flash (assoc params :errors {})))))))


;ADD PART
(defn add-part [{:keys [params]}]
  (if-let [errors (validate-addPart params)]
    (-> (response/found "/addPart")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-part! params)
      (response/found "/allParts"))))

;UPDATE PART
(defn update-part [{:keys [params]}]
  (let [errors (validate-updatePart params)]
    (if errors
      (-> (response/found "/updatePart")
          (assoc :flash (assoc params :errors errors)))
      (let [part (db/get-part-by-id {:id (:idPart params)})]
        (if-not part
          (-> (response/found "/updatePart")
              (assoc :flash (assoc params :errors {:idPart "Part with ID doesnt exist!!"})))
          (do
            (db/update-part! params)
            (response/found "/allParts")))))))

;DELETE PART
(defn delete-part [{:keys [params]}]
  (let [errors (validate-onlyId params)]
    (if errors
      (-> (response/found "/deletePart")
          (assoc :flash (assoc params :errors errors)))
      (let [part (db/get-part-by-id params)]
        (if-not part
          (-> (response/found "/deletePart")
              (assoc :flash (assoc params :errors {:id "Part with ID doesnt exist!!"})))
          (do
            (db/delete-part! params)
            (response/found "/allParts")))))))

;BUY PART
(defn buy-part [{:keys [params]}]
  (let [errors (validate-buyPart params)]
    (if errors
      (-> (response/found "/buyPart")
          (assoc :flash (assoc params :errors errors)))
      (let [part (db/get-part-by-id {:id (:idPart params)})]
        (if-not part
          (-> (response/found "/buyPart")
              (assoc :flash (assoc params :errors {:idPart "Part with ID doesnt exist!!"})))
          (do
            (db/add-order! params)
            (response/found "/orders")))))))

;COMPLETE ORDER
(defn complete-order [{:keys [params]}]
  (let [errors (validate-onlyId params)]
    (if errors
      (-> (response/found "/orders")
          (assoc :flash (assoc params :errors errors)))
      (let [part (db/get-order-by-id params)]
        (if-not part
          (-> (response/found "/orders")
              (assoc :flash (assoc params :errors {:id "Order with ID doesnt exist or is already sent!!"})))
          (do
            (db/order-sent params)
            (response/found "/sentOrders")))))))







;AUTH
(defn create-user [email password]
  (jdbc/with-transaction [t-conn db/*db*]
                         (if-not (empty? (db/get-user-by-id t-conn {:email email}))
                           (throw (ex-info "User already exists!"
                                           {:carservice/error-id ::duplicate-user1
                                            :error               "User already exists!"}))
                           (db/create-user! t-conn
                                            {:email    email
                                             :password (hashers/derive password)}))))

(defn authenticate-user [email password]
  (let [{hashed :password :as user} (db/get-user-by-id {:email email})]
    (when (hashers/check password hashed)
      (dissoc user :password))))


; --- ROUTING --------------------------------------------------------------------------

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}

   ;API
   ["/searchForBigService" {:post search-parts}]
   ["/searchForMinorService" {:post search-minor}]
   ["/searchForSensors" {:post search-sensor}]
   ["/addNewPart" {:post add-part}]
   ["/updateNewPart" {:post update-part}]
   ["/deleteOldPart" {:post delete-part}]
   ["/buyAPart" {:post buy-part}]
   ["/completeOrder" {:post complete-order}]

   ;["/test" {:post  (create-user "test44" "test")}]

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
   ["/buyPart" {:get buyPart-page}]
   ["/orders" {:get orders-page}]
   ["/sentOrders" {:get sentOrders-page}]

   ["/login" {:get login-page}]

   ["/loginAdmin"
    {:post {:parameters
            {:body
             {:email    string?
              :password string?}}
            :responses
            {200
             {:body
              {:identity
               {:email      string?
                :created_at inst?}}}
             401
             {:body
              {:message string?}}}
            :handler
            (fn [{{{:keys [email password]} :body} :parameters
                  session                          :session}]
              ;CANT PASS Email AND PASSWORD ---------------------------------
              (println "Email:" email)
              (println "password:" password)
              ;THIS PART DOESNT WORK -----------------------------------------
              (if-some [user (authenticate-user "test1" "test1")]
                (->
                  (response/ok
                    {:identity user})
                  (assoc :session (assoc session
                                    :identity
                                    user)))
                (response/unauthorized
                  {:message "Incorrect login or password."})))}}]

   ])


