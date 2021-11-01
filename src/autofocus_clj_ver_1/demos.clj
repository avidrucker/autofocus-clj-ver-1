(ns autofocus-clj-ver-1.demos
  (:require
   ;; [clojure.string :as str]
   ))
;; [clojure.pprint :as p]

;; (defn print-before-after [number before after]
;;   (binding [p/*print-right-margin* 30]
;;     (println (str "start #" number))
;;     (println "before:")
;;     (p/pprint (stringify-list (get before :current-list)))
;;     (println "after:")
;;     (p/pprint (stringify-list (get after :current-list)
;;    (println (str "end #" number))))

;; question: How can these mappings be stored in
;;           one place for effective access?
;; TODO: clean up duplicate data
;; (def states-n-choices
;;   [{:text "Add new to-do"
;;     :state :adding}
;;    {:text "Review to-do list"
;;     :state :reviewing}
;;    {:text "Focus on your priority to-do"
;;     :state :focusing}
;;    {:text "Read about AutoFocus"
;;     :state :about}
;;    {:text "See the help section"
;;     :state :help}
;;    {:text "Quit application"
;;     :state :quitting}])

;; (menu-text-to-state "Add new to-do")

(defn run-app-test
  "Create a while-loop-like application that ends
   when the user enters a specific text in the console"
  []
  (loop [] ;; original: (loop [running true]
    (println "Type 'done' when you are ready:")
    (let [input-text (read-line)]
      (when (not= input-text "done")
        
        (println "Still running...")
        (recur)))) ;; original: (recur [true]))))
  (println "All done!"))

;; (defn -main []
;;   ;; (io/io-demo-1)
;;   ;; (deliver-cli-menu cli-menu)
;;   ;; (run-app-test)
;;   (cli/run-cli-app))

;; (-main)

