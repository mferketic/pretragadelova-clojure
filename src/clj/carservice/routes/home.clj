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
             ;PART THAT DOESN'T WORK ------------------------------------------------
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

(defn addPart-page [{:keys [flash] :as request}]
  (layout/render
    request
    "parts/createPart.html"
    (merge {:models (db/get-models)}
           {:types (db/get-types)}
           (select-keys flash [:type1 :model1 :partName :fromYear :toYear :price :description :errors]))))

(defn updatePart-page [{:keys [flash] :as request}]
  (layout/render
    request
    "parts/updatePart.html"
    (merge {:models (db/get-models)}
           {:types (db/get-types)}
           (select-keys flash [:type1 :model1 :partName :fromYear :toYear :price :description :errors]))))

(defn order-page [request]
  (layout/render request "about.html"))

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
    {:message  "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]])

(def addPart-schema
  [[:type1
    st/required]
   [:model1
    st/required]
   [:partName
    st/required
    st/string]
   [:fromYear
    st/required
    {:message  "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:toYear
    st/required
    {:message  "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:price
    st/required
    {:message  "ID must be positive number!"
     :validate (fn [year] (> (Integer/parseInt (re-find #"\A-?\d+" year)) 0))}]
   [:description
    st/required
    st/string]])


; --- VALIDATION -----------------------------------------------------------------

;Search part
(defn validate-search [params]
  (first (st/validate params search-schema)))

;IdCheck
;(defn validate-id [params]
;(first (st/validate params idCheck-schema)))

;Add part
(defn validate-addPart [params]
  (first (st/validate params addPart-schema)))



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

;ADD PART
(defn add-part [{:keys [params]}]
  (if-let [errors (validate-addPart params)]
    (-> (response/found "/addPart")
        ;OBRISI POSLE -------------------------------------------------
        (println "Email:" params)
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-part! params)
      (response/found "/allParts"))))








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
   ["/addNewPart" {:post add-part}]
   ["/updateNewPart" {:post add-part}]
   ;["/test" {:post  (create-user "test2" "test")}]

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
   ["/orders" {:get order-page}]

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


