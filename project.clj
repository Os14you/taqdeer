(defproject taqdeer "0.0.1"
  :description "Program that calculate and manage your grades"
  :url "https://github.com/Os14you/taqdeer" 
  :dependencies [[org.clojure/clojure "1.12.2"]]
  :main ^:skip-aot taqdeer.core
  :target-path "target/%s"
  :repl-options {:init-ns taqdeer.core}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
