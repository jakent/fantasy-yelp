(ns dev
  (:require [figwheel-sidecar.repl-api :as repl-api]
            [mount.core :as mount :refer [start stop]]
            [fantasy-yelp.web :as web]))

(defn restart []
  (mount/stop)
  (mount/start))

(defn figwheel-up []
  (do (restart)
      (repl-api/start-figwheel!)
      (repl-api/cljs-repl)))
