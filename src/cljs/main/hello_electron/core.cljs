(ns hello-electron.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defonce path          (js/require "path"))
(defonce url           (js/require "url"))

(defonce electron      (js/require "electron"))
(defonce app           (.-app electron))
(defonce BrowserWindow (.-BrowserWindow electron))

(def win (atom nil))

(defn create-window
  []
  (reset! win (BrowserWindow. (clj->js {:width 800 :height 600})))
  (.loadURL @win (.format url (clj->js {:pathname (.join path (js* "__dirname") "../../../index.html")
                                        :protocol "file"
                                        :slashes true})))
  (.openDevTools (.-webContents @win))
  (.on @win "closed" (fn []
                       ;; (println "window: closed")
                       (reset! win nil))))

(defn -main [& args]
  (.on app "ready" (fn []
                     ;; (println "app: ready")
                     (create-window)))
  
  (.on app "window-all-closed"
     (fn []
       ;; (println "app: window-all-closed")
       (when (not= (.-platform nodejs/process) "darwin")
         (.quit app))))
  
  (.on app "activate"
     (fn []
       ;; (println "app: activate")
       (when-not @win
         (create-window)))))

(set! *main-cli-fn* -main)
