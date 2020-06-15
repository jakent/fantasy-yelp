(ns dev
  (:require [figwheel-sidecar.repl-api :as repl-api]
            [mount.core :as mount :refer [start stop]]
            [fantasy-yelp.web :as web]))

(defn figwheel-up []
  (do (repl-api/start-figwheel!)
      (repl-api/cljs-repl)))

(defn restart []
  (mount/stop)
  (mount/start))