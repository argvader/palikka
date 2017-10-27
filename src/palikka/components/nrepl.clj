(ns palikka.components.nrepl
  (:require [clojure.tools.nrepl.server :as nrepl]
            [com.stuartsierra.component :as component]
            [schema.core :as s]
            [clojure.tools.logging :as log]
            [palikka.coerce :as c]))

(s/defschema Config
  {:port s/Int
   s/Keyword s/Any})

(defrecord Nrepl [component-config nrepl]
  component/Lifecycle
  (start [this]
    (if-not nrepl
      (do
        (log/infof "Starting nrepl server on nrepl://%s:%s" "localhost" port)
        (assoc this :nrepl (apply nrepl/start-server (mapcat identity component-config))))
      this))
  (stop [this]
    (when nrepl
      (try
        (nrepl/stop-server nrepl)
        (catch Throwable t
          (log/warn t "Error when closing nrepl server"))))
    (assoc this :nrepl nil)))

(defn create [config]
  (map->Nrepl {:component-config (c/env-coerce Config config)}))
