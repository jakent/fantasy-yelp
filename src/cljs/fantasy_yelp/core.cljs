(ns fantasy-yelp.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [goog.dom :as gdom]))

(defonce locations (r/atom []))

(go (let [response (<! (http/get "/api/locations"))]
      (reset! locations (response :body))))

(defonce active (r/atom {}))

(defn attribute [item-name]
  (fn [[key value]]
    [:div.attribute {:key (str item-name key value)}
     [:div.attribute-name (str (name key) ":")]
     [:div {:dangerouslySetInnerHTML {:__html "&nbsp;"}}]
     [:div value]]))

(defn item [{:keys [name description attributes]}]
  [:div.item
   [:div.item-name name]
   [:div.item-description description]
   (map (attribute name) attributes)])

(defn destination [index location]
  [:div.destination {:style    (location :style)
                     :key      (location :name)
                     :on-click #(reset! active location)}
   (inc index)])

(defn location [{:keys [name description items]}]
  (if name
    [:div.location
     [:div.location-name name]
     [:div.location-description description]
     [:div.location-inventory "Inventory"]
     [:div.gold]
     (map (fn [{:keys [name description] :as detail}]
            [:div {:key (str name description)}
             [item detail]
             [:div.gold]])
          items)]))

(defn app-container []
  [:div.app
   [:div.map {:on-click (fn [event]
                          (let [offset #(- % 15)]
                            (println {:top  (offset (.. event -nativeEvent -offsetY))
                                      :left (offset (.. event -nativeEvent -offsetX))})))}
    [:div.yelp]
    (map-indexed destination @locations)]
   [location @active]])

(r/render-component [app-container] (gdom/getElement "app"))
