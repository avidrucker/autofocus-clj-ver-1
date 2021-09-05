(ns autofocus-clj-ver-1.demos
  (:require
   ;; [autofocus-clj-ver-1.io :as io]
   [clojure.string :as str]))
;; [clojure.pprint :as p]

;; (defn print-before-after [number before after]
;;   (binding [p/*print-right-margin* 30]
;;     (println (str "start #" number))
;;     (println "before:")
;;     (p/pprint (stringify-list (get before :current-list)))
;;     (println "after:")
;;     (p/pprint (stringify-list (get after :current-list)
;;    (println (str "end #" number))))

;; TODO: clean up duplicate data
(def app-state
  {:options [;; IDEA: implement a splash screen
             ;; question: What are the trade-offs of
             ;; having a splash screen?
             ;; "splash" ;; press any key to start
             "menu" ;; choose from the following menu
             "adding"
             ;; 1. enter text
             ;; 2. hit enter to confirm
             "reviewing"
             ;; 1. make first choice (y/n/q)
             ;; 2. make 2nd choice (y/n/q)
             ;; ...
             "focusing"
             ;; 1. press any key to end focus session
             ;; 2. answer question, "Is there remaining work? (y)"
             ;; IDEA: enable quitting of focus session w/o marking done
             "about" ;; press enter key to return to menu
             "help" ;; press enter key to return to menu
             "quitting" ;; confirm "are you sure you want to quit?" (y/n)
             "confirming-quit" ;; prints 'bye' & closes program
             ]})

;; TODO: clean up duplicate data
(defn transition-state
  "Takes in a current app state and menu selection,
   and returns the new app state."
  [current option]
  (case (keyword current)
    :splash :menu

    ;; TODO: implement logic to pick from a valid option
    :menu (keyword ((get app-state :options) option))
    :adding :menu
    :reviewing :menu

    ;; TODO: add confirmation step to focusing to
    ;;       enable user to add a duplicate to-do item
    :focusing :menu
    :about :menu
    :help :menu

    ;; TODO: enable the user to confirm they are ready
    ;;       to quit the application (and not a mispress)
    :quitting (keyword ((get app-state :options) option))))

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

;; TODO: clean up duplicate data
(defn menu-text-to-state [input-text]
  (case input-text
    "Add new to-do" :adding
    "Review to-do list" :reviewing
    "Focus on your priority to-do" :focusing
    "Read about AutoFocus" :about
    "See the help section" :help
    "Quit application" :quitting
    ))

;; (menu-text-to-state "Add new to-do")

(def cli-menu
  {:prompt "Choose from the following menu:"
   :options ["Add new to-do"
             "Review to-do list"
             "Focus on your priority to-do"
             "Read about AutoFocus"
             "See the help section"
             {:id "q" :text "Quit application"}]})

(def quit-confirmation
  {:prompt "Are you sure you want to close the program now?"
   :options [{:id "y"
              :text "Yes"}
             {:id "n"
              :text "No"}]})

(defn parse-options [options]
  (map (fn [opt idx]
         (if (string? opt)
           {:id (str (inc idx)) :text opt}
           opt)) options (range)))

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

(defn print-prompt-and-options [prompt options]
  (when prompt
    (println)
    (println prompt)
    (println))
  (doseq [{:keys [id text]} options]
    (println (str " [" id "]") text))
  (println))

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

(defn say-something-about-invalid-input [input-text]
  (if (= input-text "")
    (println (str "\n-- You did not enter any text," 
                  "\nplease try again."))
    (println
     (format
      (str "\n-- The entered text '%s' isn't a " 
           "\nvalid choice, please try again.")
      input-text))))


;; question: can this function be made pure?
;; reference: https://clojuredocs.org/clojure.core/read-line#example-5f3ffb46e4b0b1e3652d7382
(defn deliver-cli-menu
  [{:keys [prompt options]}]
  (let [options       (parse-options options)
        valid-options (set (map :id options))]
    (loop []
      (print-prompt-and-options prompt options)

      (let [input-text (str/trim (read-line))]
        (cond
          (= (str/lower-case input-text) "q")
          :cancelled

          (or (= input-text "")
              (not (valid-options input-text)))
          (do
            (say-something-about-invalid-input input-text)
            (recur))

          :else
          ;; returns the valid menu choice as a hashamp
          (first (filter #(= input-text (:id %)) options
                         )))))))

;; (deliver-cli-menu cli-menu)

;; TODO: update logic to use state names
;;       instead of menu text options
(defn run-app
  []
  (println "Welcome to AutoFocus!")
  (loop [current-state {:state :menu}]
    ;; DEBUGGING
    (println "Current state is:")
    (println current-state)
    ;; TODO: implement conditional logic to load state
    ;; depending on 'current-state'
    (let [new-state (deliver-cli-menu cli-menu)]
      ;; TODO: update logic to use state names
      ;;       instead of menu text options
      ;; DEBUGGING
      ;; (println "New state will be:")
      ;; (if (keyword? new-state)
      ;;   (println new-state)
      ;;   (println (keyword
      ;;             (menu-text-to-state
      ;;              (get new-state :text)))))
      (when
          (not= new-state :cancelled)
        ;; DEBUGGING
        ;; (println "Still running...")
        (recur {:state 
                (keyword (menu-text-to-state
                          (get new-state :text)))}))))
  (println "Bye!"))

(defn -main []
  ;; (io/io-demo-1)
  ;; (deliver-cli-menu cli-menu)
  ;; (run-app-test)
  (run-app))

(-main)
