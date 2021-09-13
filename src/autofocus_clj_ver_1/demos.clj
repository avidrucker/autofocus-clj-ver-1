(ns autofocus-clj-ver-1.demos
  (:require
   ;; [autofocus-clj-ver-1.io :as io]
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

(def quit-confirmation
  {:prompt "Are you sure you want to close the program now?"
   :options [{:id "y"
              :text "Yes"}
             {:id "n"
              :text "No"}]})

;; TODO: implement stub
;; (defn deliver-yes-no-question [prompt]
;;   (let [options (parse-options [{:id "y"
;;                  :text "Yes"}
;;                 {:id "n"
;;                  :text "No"}])
;;         valid-options (set (map :id options))]
;;     ;; code goes here
;;        ))

;; TODO: implement stub
;; (defn deliver-yes-no-quit-question [prompt]
;;   (let [options (parse-options [{:id "y"
;;                   :text "Yes"}
;;                  {:id "n"
;;                   :text "No"}
;;                  {:id "q"
;;                   :text "Quit"}])
;;         valid-options (set (map :id options))]
;;     ;; code goes here
;;     ))

(defn run-app-test
  "Create a while-loop-like application that ends
   when the user enters a specific text in the console"
  []
  (loop [running true]
    (println "Type 'done' when you are ready:")
    (let [input-text (read-line)]
      (when (not= input-text "done")
        
        (println "Still running...")
        (recur [true]))))
  (println "All done!"))

;; (deliver-cli-menu cli-menu)

(defn -main []
  ;; (io/io-demo-1)
  ;; (deliver-cli-menu cli-menu)
  ;; (run-app-test)
  ;; (run-app)
  )

;; (-main)
