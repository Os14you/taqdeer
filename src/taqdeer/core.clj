(ns taqdeer.core
  (:gen-class))

(def grade-map {:A+ 4 :A 3.7 :B+ 3.3 :B 3 :C+ 2.7 :C 2.4 :D+ 2 :D 1.7 :F 0})
(def sem1-courses {:AI01 { :grade "A"
                      :hours 2} 
              :AI02 { :grade "A+"
                      :hours 3} 
              :AI03 { :grade "D"
                      :hours 2} 
              :CS02 { :grade "C"
                      :hours 2} 
              :BI01 { :grade "B+"
                      :hours 3}})

(defn sem-gpa 
  "Calculate one semester GPA"
  [sem-courses]
  (let [total-points (->> sem-courses
                           (vals)
                           (map (fn [{:keys [grade hours]}]
                                  (* hours (get grade-map (keyword grade)))))
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
    (double (/ sum-gpas divisor))))

(defn -main
  [& args] 
  (def gpa (sem-gpa sem1-courses))
  (println "Your GPA is: " (format "%.3f" gpa))
  (def cum-gpa (cumulative-gpa gpa 3.2 3.9))
  (println "Your Cumulative GPA is: " (format "%.3f" cum-gpa)))
