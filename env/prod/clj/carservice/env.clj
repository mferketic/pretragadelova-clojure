(ns carservice.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[carservice started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[carservice has shut down successfully]=-"))
   :middleware identity})
