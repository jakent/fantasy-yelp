# fantasy-yelp

## spin it up

you have a few options

### development server

```
> lein repl
(figwheel-up)
```

go to http://localhost:3450/

## main method

```
> lein run -m fantasy-yelp.web
```

go to http://localhost:5000/

## build the jar

```
> lein uberjar
> java -cp target/fantasy_yelp.jar clojure.main -m fantasy-yelp.web
```

go to http://localhost:5000/