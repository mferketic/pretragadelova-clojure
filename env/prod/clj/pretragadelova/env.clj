(ns pretragadelova.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[pretragadelova started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[pretragadelova has shut down successfully]=-"))
   :middleware identity})
