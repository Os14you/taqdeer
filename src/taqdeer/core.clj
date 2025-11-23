(ns taqdeer.core
  (:require [clojure.edn :as edn])
  (:gen-class))

(def sem1-courses {:AI01 {:grade "A"  :hours 2}
                   :AI02 {:grade "A+" :hours 3}
                   :AI03 {:grade "D"  :hours 2} 
                   :CS02 {:grade "C"  :hours 2}
                   :BI01 {:grade "B+" :hours 3}})

(defn load-config
  [filename]
  (edn/read-string (slurp filename)))

(defn grade->points
  [grade scale]
  (let [grade-key (keyword grade)]
    (get scale grade-key 0)))

(defn sem-gpa 
  "Calculate one semester GPA"
  [sem-courses grade-scale]
  (let [total-points (->> sem-courses
                           (vals)
                           (map (fn [{:keys [grade hours]}]
                                  (* hours (grade->points grade grade-scale))))
                           (reduce +))
         total-hours (->> sem-courses
                          (vals)
                          (map (fn [{:keys [hours]}] hours))
                          (reduce +))]
     (double (/ total-points total-hours))))

(defn cumulative-gpa 
  "Calculate the cumulative GPA, takes a list of GPAs"
  [& gpas]
  (let [sum-gpas (->> gpas
                      (reduce +))
        divisor  (count gpas)]
    (format "%.3f" (double (/ sum-gpas divisor)))))

(defn -main
  [& args] 
  (let [config (load-config "config.edn")
        scale (:grade-scale config)]
    (def gpa (sem-gpa sem1-courses scale)))
  (println "Your GPA is: " (format "%.3f" gpa))
  (def cum-gpa (cumulative-gpa gpa 3.2 3.9))
  (println "Your Cumulative GPA is: " cum-gpa))
