(defproject hello-electron "0.1.0-SNAPSHOT"
  :description "Hello Electron form ClojureScript"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]]
  :plugins [[lein-cljsbuild "1.1.4"]]
  :aliases {"clean-dev"  ["clean"]
            "clean-prod" ["with-profile" "prod" "clean"]
            "clean-all"  ["do"
                          ["clean-dev"]
                          ["clean-prod"]]
            "build-dev"  ["do"
                          ["clean-dev"]
                          ["cljsbuild" "once" "dev-main"]
                          ["cljsbuild" "once" "dev-front"]]
            "build-prod" ["do"
                          ["clean-prod"]
                          ["cljsbuild" "once" "prod-main"]]
            "build-all"  ["do"
                          ["build-dev"]
                          ["build-prod"]]}
  :profiles {:dev  {:clean-targets [:target-path
                                    "app/dev/js/main"
                                    "app/dev/js/front"]}
             :prod {:clean-targets [:target-path "app/prod/js/main"]}}
  :cljsbuild
  {:builds
   {:dev-main {:source-paths ["src/cljs/main"
                              "src/cljs/dev-main"]
               :compiler {:target :nodejs
                          :main "hello-electron.core"
                          :output-to "app/dev/js/main/main.js"
                          :output-dir "app/dev/js/main"
                          :optimizations :none}}

    :prod-main {:source-paths ["src/cljs/main"
                               "src/cljs/prod-main"]
                :compiler {:target :nodejs
                           :output-to "app/prod/js/main/main.js"
                           :output-dir "app/prod/js/main"
                           :optimizations :simple}}

    :dev-front {:source-paths ["src/cljs/front"]
                :compiler {:main "hello-electron.core"
                           :output-to "app/dev/js/front/main.js"
                           :output-dir "app/dev/js/front"
                           :asset-path "js/front"
                           :optimizations :none}}}})
