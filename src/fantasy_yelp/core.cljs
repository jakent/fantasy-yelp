(ns fantasy-yelp.core
  (:require [reagent.core :as r]
            [goog.dom :as gdom]))


(def data
  [{:name        "Padded Armor"
    :description "light armor (armor)"
    :attributes  {"AC"          11
                  "Category"    "Items"
                  "Item Rarity" "Standard"
                  "Stealth"     "Disadvantage"
                  "Weight"      8}}

   {:name        "Hide Armor"
    :description "medium armor (armor)"
    :attributes  {"AC"          12
                  "Category"    "Items"
                  "Item Rarity" "Standard"
                  "Stealth"     "Disadvantage"
                  "Weight"      12}}

   {:name        "Wand Of Magic Detection"
    :description "adventuring gear (wand)"
    :attributes  {"Category"    "Items"
                  "Item Rarity" "Uncommon"
                  "Weight"      1}}])

(def locations
  [{:name        "Triboar Supply"
    :description "humble store"
    :style       {:top 145 :left 251}
    :items       data}

   {:name        "Triboar Supply2"
    :description "humble store"
    :style       {:top 326 :left 275}
    :items       [(get data 1)]}])

(defonce active (r/atom {}))

(defn attribute [{:keys [name value]}]
  [:div.attribute
   [:div.attribute-name (str name ":")]
   [:div {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
   [:div value]])

(defn item [{:keys [name description attributes]}]
  [:div.item
   [:div.item-name name]
   [:div.item-description description]
   (map (fn [[k v]]
          [attribute {:key (str name k) :name k :value v}])
        attributes)])

(defn app-container []
  [:div.app
   [:div {:style {:display "flex"
                  :height  "100%"
                  :overflow "auto"}}
    [:div.map
     [:div.yelp]
     (map-indexed (fn [index
                       {:keys [style name] :as l}]
                    [:div.location {:style    style
                                    :key      name
                                    :on-click (fn [_] (reset! active l))}
                     (inc index)])
                  locations)]
    [:div {:style {:width "100%"}}
     [:div.gold]
     (map (fn [{:keys [name description] :as detail}]
            [:div {:key (str name description)}
             [item detail]
             [:div.gold]])
          (@active :items))
     ]
    ]])

(r/render-component [app-container] (gdom/getElement "app"))

;; install the service worker
(when (exists? js/navigator.serviceWorker)
  (-> js/navigator
      .-serviceWorker
      (.register "/sw.js")
      (.then #(js/console.log "Server worker registered."))))
