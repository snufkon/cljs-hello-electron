(ns hello-electron.core
  (:require [cljs.nodejs :as nodejs]
            [hello-electron.config :as config]))

(nodejs/enable-util-print!)

(defonce electron      (js/require "electron"))
(defonce app           (.-app electron))
(defonce BrowserWindow (.-BrowserWindow electron))

(defonce win (atom nil))

(defn- create-window
  []
  (reset! win (BrowserWindow. (clj->js {:width 800 :height 600 :show false})))
  (.loadURL @win config/index-url)
  (.openDevTools (.-webContents @win))
  (.on @win "closed" (fn []
                       ;; (println "window: closed")
                       (reset! win nil)))
  (.on @win "ready-to-show" (fn []
                              ;; (println "window: ready-to-show")
                              (.showInactive @win))))

(defn- destroy-window
  []
  (.destroy @win)
  (reset! win nil))

(defn- remove-listeners
  []
  (.removeAllListeners app "ready")
  (.removeAllListeners app "activate")
  (.removeAllListeners app "window-all-closed"))

(defn- add-listeners
  []
  (.on app "ready" create-window)
  (.on app "activate" (fn []
                        ;;(println "app: activate")
                        (when-not @win
                          (create-window))))
  (.on app "window-all-closed"
     (fn []
       ;; (println "app: window-all-closed")
       (when (not= (.-platform nodejs/process) "darwin")
         (.quit app)))))

(defn -main [& args]
  (add-listeners))

(defn reload
  []
  (remove-listeners)
  (add-listeners)
  (destroy-window)
  (create-window))

(set! *main-cli-fn* -main)
