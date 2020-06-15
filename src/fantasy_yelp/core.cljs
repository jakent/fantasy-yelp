(ns fantasy-yelp.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [goog.dom :as gdom]))

(defonce locations (r/atom []))

(go (let [response (<! (http/get "/api/foo"
                                 {:with-credentials? false}))]
      (reset! locations (response :body))))

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
                  @locations)]
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
