(defproject hello-electron "0.1.0-SNAPSHOT"
  :description "Hello Electron form ClojureScript"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]]
  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.8"]
            [lein-sassy "1.0.7"]
            [lein-externs "0.1.6"]]
  :aliases {"figwheel-main"  ["with-profile" "figwheel-main" "figwheel" "dev-main"]
            "figwheel-front" ["with-profile" "figwheel-front" "figwheel" "dev-front"]
            "externs-prod"   ["externs" "prod-front" "app/prod/js/externs_front.js"]
            "clean-dev"  ["clean"]
            "clean-prod" ["with-profile" "prod" "clean"]
            "clean-all"  ["do"
                          ["clean-dev"]
                          ["clean-prod"]]
            "build-dev"  ["do"
                          ["clean-dev"]
                          ["cljsbuild" "once" "dev-main"]
                          ["cljsbuild" "once" "dev-front"]
                          ["sass" "once"]]
            "build-prod" ["do"
                          ["clean-prod"]
                          ["externs-prod"]
                          ["cljsbuild" "once" "prod-main"]
                          ["cljsbuild" "once" "prod-front"]
                          ["with-profile" "prod" "sass" "once"]]
            "build-all"  ["do"
                          ["build-dev"]
                          ["build-prod"]]}
  :profiles {:dev  {:clean-targets [:target-path
                                    "app/dev/js/main"
                                    "app/dev/js/front"
                                    "app/dev/css/style.css"]
                    :sass {:src "src/scss"
                           :dst "app/dev/css"
                           :style :nested}}
             :prod {:clean-targets [:target-path
                                    "app/prod/js/main"
                                    "app/prod/js/front"
                                    "app/prod/css/style.css"]
                    :sass {:src "src/scss"
                           :dst "app/prod/css"
                           :style :compressed}}
             :figwheel-main  {:figwheel {:server-port 3500
                                         :server-logfile "figwheel-main.log"}}
             :figwheel-front {:figwheel {:server-port 3600
                                         :server-logfile "figwheel-front.log"
                                         :css-dirs ["app/dev/css"]}}}
  :cljsbuild
  {:builds
   {:dev-main {:source-paths ["src/cljs/main"
                              "src/cljs/dev-main"]
               :figwheel {:on-jsload "hello-electron.core/reload"}
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
                :figwheel true
                :compiler {:main "hello-electron.core"
                           :output-to "app/dev/js/front/main.js"
                           :output-dir "app/dev/js/front"
                           :asset-path "js/front"
                           :optimizations :none}}

    :prod-front {:source-paths ["src/cljs/front"]
                 :compiler {:output-to "app/prod/js/front/main.js"
                            :output-dir "app/prod/js/front"
                            :optimizations :advanced
                            :externs ["app/prod/js/externs_front.js"]}}}}
  :figwheel {:hawk-options {:watcher :polling}})
