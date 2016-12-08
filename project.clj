(defproject hello-electron "0.1.0-SNAPSHOT"
  :description "Hello Electron form ClojureScript"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]]
  :plugins [[lein-cljsbuild "1.1.4"]]
  :clean-targets [:target-path "app/dev/js/main"]
  :cljsbuild
  {:builds
   {:dev-main {:source-paths ["src/cljs/main"]
               :compiler {:target :nodejs
                          :main "hello-electron.core"
                          :output-to "app/dev/js/main/main.js"
                          :output-dir "app/dev/js/main"
                          :optimizations :none}}}})
