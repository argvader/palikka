(ns palikka.components.rabbitmq
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [suspendable.core :as suspendable]
            [schema.core :as s]
            [langohr.core :as rmq]
            [palikka.coerce :as c])
  (:import [com.rabbitmq.client Connection]))

(s/defschema Config
  {(s/optional-key :host) s/Str
   (s/optional-key :port) s/Int
   (s/optional-key :username) s/Str
   (s/optional-key :password) s/Str
   (s/optional-key :vhost) s/Str
   (s/optional-key :ssl) s/Bool
   (s/optional-key :uri) s/Str
   s/Keyword s/Str})

(defrecord RabbitMQ [^Connection mq config]
  component/Lifecycle
  (start [this]
    (log/infof "Connecting to RabbitMQ on %s:%d" (:host config) (:port config))
    (if-not mq
      (assoc this :mq (rmq/connect config))
      this))
  (stop [this]
    (when (and mq (.isOpen mq))
      (log/info "Closing RabbitMQ connection")
      (try
        (.close mq)
        (catch Throwable t
          (log/warn t "Error when closing RabbitMQ collection"))))
    (assoc this :mq nil))

  suspendable/Suspendable
  (suspend [this]
    this)
  (resume [this old-component]
    (if (= config (:config old-component))
      (assoc this :mq (:mq old-component))
      (do (component/stop old-component)
          (component/start this)))))

(defn create [config]
  (map->RabbitMQ {:config (c/env-coerce Config config)}))
