(ns fantasy-yelp.web
  (:require [clojure.java.io :as io]
            [org.httpkit.server :as server]
            [mount.core :as mount]
            [cprop.core :as cp]
            [cprop.source :as cps]
            [reitit.ring :as ring]))

(defn app []
  (ring/ring-handler
    (ring/router
      [["/" {:get (constantly
                    {:status 200
                     :body   (slurp (io/resource "public/index.html"))})}]
       ["/api/foo" (fn [_]
                     {:status 200
                      :body   "sucess"})]])
    (ring/routes
      (ring/create-resource-handler
        {:path "/"})
      (ring/create-default-handler))))

(mount/defstate config :start (cp/load-config :merge [(cps/from-env)])
                :stop :stopped)

(defn start-server [{:keys [port]}]
  (server/run-server (app) {:port port}))

(mount/defstate ^{:on-reload :noop} http-server
                :start (start-server config)
                :stop (http-server))

(defn -main [& _]
  (mount/start)
  (println "server started at port:" (config :port)))
