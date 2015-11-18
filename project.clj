(defproject metosin/palikka "0.3.1-SNAPSHOT"
  :description "Metosin palikka"
  :url "https://github.com/metosin/palikka"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}
  :dependencies [[potemkin "0.4.1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [prismatic/schema "1.0.3"]
                 [metosin/schema-tools "0.6.2"]
                 [com.stuartsierra/component "0.3.0"]
                 [suspendable "0.1.0"]]
  :plugins [[lein-codox "0.9.0"]]

  :codox {:source-uri "http://github.com/metosin/palikka/blob/master/{filepath}#L{line}"}

  :profiles {:dev {:plugins [[jonase/eastwood "0.2.1"]
                             [lein-midje "3.2"]]
                   :source-paths ["examples"]
                   :resource-paths ["test-resources"]
                   :dependencies [[org.clojure/clojure "1.7.0"]
                                  [midje "1.8.1"]
                                  [clj-http "2.0.0"]
                                  [reloaded.repl "0.2.1"]
                                  [metosin/maailma "0.2.0-SNAPSHOT"]

                                  ; Components
                                  [com.novemberain/monger "3.0.1"]
                                  [hikari-cp "1.3.1"]
                                  [http-kit "2.1.19"]
                                  [postgresql/postgresql "9.3-1102.jdbc41"]
                                  [org.flywaydb/flyway-core "3.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [com.datomic/datomic-free "0.9.5327" :exclusions [org.slf4j/slf4j-nop joda-time]]
                                  [com.novemberain/langohr "3.4.1"]

                                  ; Logging
                                  [ch.qos.logback/logback-classic "1.1.3"]
                                  [org.slf4j/slf4j-api "1.7.12"]
                                  ]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0-beta2"]]}}
  :aliases {"all" ["with-profile" "dev:dev,1.8"]
            "test-clj"  ["all" "do" ["midje"] ["check"]]})
