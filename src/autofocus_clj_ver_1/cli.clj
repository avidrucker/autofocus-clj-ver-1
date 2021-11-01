(ns autofocus-clj-ver-1.cli
(:require
[clojure.string :as str]
 ;; TODO: remove app require here, and instead combine app and CLI in core
 [autofocus-clj-ver-1.app :as app]
 )
)

(defn- -print-prompt-and-options [prompt options]
  (when prompt
    (println)
    (println prompt)
    (println))
  (doseq [{:keys [id text]} options]
    (println (str " [" id "]") text))
  (println))

(defn- -say-something-about-invalid-input [input-text]
  (if (= input-text "")
    (println (str "\n-- You did not enter any text,"
                  "\nplease try again."))
    (println
     (format
      (str "\n-- The entered text '%s' isn't a "
           "\nvalid choice, please try again.")
      input-text))))

(defn- -parse-options [options]
  (map (fn [opt idx]
         (if (string? opt)
           {:id (str (inc idx)) :text opt}
           opt)) options (range)))

;; question: can this function be made pure?
;; reference: https://clojuredocs.org/clojure.core/read-line#example-5f3ffb46e4b0b1e3652d7382
(defn deliver-cli-menu
  [{:keys [prompt options]}]
  (let [options       (-parse-options options)
        valid-options (set (map :id options))]
    (loop []
      (-print-prompt-and-options prompt options)

      (let [input-text (str/trim (read-line))]
        (cond
          (= (str/lower-case input-text) "q")
          :cancelled

          (or (= input-text "")
              (not (valid-options input-text)))
          (do
            (-say-something-about-invalid-input input-text)
            (recur))

          :else
          ;; returns the valid menu choice as a hashamp
          (first (filter #(= input-text (:id %)) options)))))))

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

;; TODO: build this dynamically from ui-fsm
;; (def cli-menu
;;   {:prompt "Choose from the following menu:"
;;    :options ["Add new to-do"
;;              "Review to-do list"
;;              "Focus on your priority to-do"
;;              "Read about AutoFocus"
;;              "See the help section"
;;              {:id "q" :text "Quit application"}]})

;; TODO: use ui-fsm to replace this
;; TODO: clean up duplicate data
(defn menu-text-to-state [input-text]
  (case input-text
    "Add new to-do" :adding
    "Review to-do list" :reviewing
    "Focus on your priority to-do" :focusing
    "Read about AutoFocus" :about
    "See the help section" :help
    "Quit application" :quitting))

(def quit-confirmation
  {:prompt "Are you sure you want to close the program now?"
   :options [{:id "y"
              :text "Yes"}
             {:id "n"
              :text "No"}]})

;; TODO: update logic to use state names
;;       instead of menu text options
(defn run-cli-app
  [initial-program-state]
  
  (loop [{:keys [current-ui the-list t-time]} initial-program-state] ;; before: [current state {:state :menu}]
    (println "Current state is:" current-ui) ;; DEBUGGING

    ;; dispatch on current state
    (let [new-state (cond
                      (= 'Start current-ui)
                      (do (println "Welcome to AutoFocus!") :initial)

                      (= 'Menu current-ui)
                      (deliver-cli-menu {:prompt "Choose from the following menu:"
                                         :options (get app/ui-fsm 'Menu)}))]
      (when
       (not= new-state :cancelled)
        ;; (println "Still running...") ;; DEBUGGING
        (recur {:state
                ;; TODO: refactor menu-text-to-state out from cli namespace
                ;;       (ie. make it so that CLI doesn't know anything about app)
                (keyword (menu-text-to-state
                          (get new-state :text)))}))))
  (println "Bye!"))

;; (defn -main []
;;   )
