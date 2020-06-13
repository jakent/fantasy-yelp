# fantasy-yelp

## start dev server

* start repl: `lein repl`
* inside repl: `figwheel-up`

## build the jar

* `lein uberjar`
* `java -cp target/fantasy_yelp.jar clojure.main -m fantasy-yelp.web`