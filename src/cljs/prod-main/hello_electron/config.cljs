(ns hello-electron.config)

(defonce path (js/require "path"))
(defonce url  (js/require "url"))

(def index-url (->> {:pathname (.join path (js* "__dirname") "../../index.html")
                     :protocol "file"
                     :slashes true}
                    clj->js
                    (.format url)))
