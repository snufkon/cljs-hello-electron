(ns hello-electron.core)

(let [node (-> "h1" js/document.getElementsByTagName (aget 0))]
  (set! (.-innerHTML node) "Hello ClojureScript World!"))
