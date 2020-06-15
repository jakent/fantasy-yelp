(defproject fantasy-yelp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name ""
            :url  ""}
  :clean-targets ^{:protect false} [:target-path "out" "resources/public/js"]
  :min-lein-version "2.5.3"
  :repl-options {:init-ns dev.repl}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.9.0-rc1"]
                 [cljs-http "0.1.46"]
                 [metosin/reitit "0.5.2"]
                 [http-kit "2.4.0-alpha6"]
                 [mount "0.1.16"]
                 [cprop "0.1.14"]]
  :plugins [[lein-environ "1.1.0" :hooks false]
            [lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.19"]]

  :figwheel {:http-server-root "public"
             :ring-handler fantasy-yelp.web/app
             :css-dirs     ["resources/public/css"]
             :server-port  3450}

  :uberjar-name "fantasy_yelp.jar"

  :source-paths ["src"]
  :resource-paths ["resources"]

  :profiles {:dev     {:dependencies [[cider/piggieback "0.4.1"]
                                      [figwheel-sidecar "0.5.18"]
                                      [binaryage/devtools "0.9.10"]]
                       :repl-options {:init-ns          dev
                                      :nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                       :source-paths ["dev"]
                       :cljsbuild    {:builds [{:id           "dev"
                                                :source-paths ["src"]
                                                :figwheel     true
                                                :compiler     {:main                 "fantasy-yelp.core"
                                                               :preloads             [devtools.preload]
                                                               :asset-path           "js/out"
                                                               :output-to            "resources/public/js/main.js"
                                                               :output-dir           "resources/public/js/out"
                                                               :optimizations        :none
                                                               :recompile-dependents true
                                                               :source-map           true}}]}}
             :uberjar {:env          {:production true}
                       :source-paths ["src"]
                       :prep-tasks   ["compile" ["cljsbuild" "once"]]
                       :cljsbuild    {:builds [{:id           "production"
                                                :source-paths ["src"]
                                                :jar          true
                                                :compiler     {:main          "fantasy-yelp.core"
                                                               :asset-path    "js/out"
                                                               :output-to     "resources/public/js/main.js"
                                                               :output-dir    "resources/public/js/out"
                                                               :optimizations :advanced
                                                               :pretty-print  false}}]}}})
