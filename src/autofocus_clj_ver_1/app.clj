(ns autofocus-clj-ver-1.app
  (:require
   [autofocus-clj-ver-1.list :as list]))

;; (def app-t (atom 0))

;; TODO: implement core that use initial-app-state to start the application
(def initial-program-state
  {:current-ui 'Start ;; UI state
   ;; domain knowledge
   :the-list []
   :t-time 0 ;; sequential time of the-list
   })

;; program usage intention state machine
;; (indicates what the UI can do at any time)
(def ui-fsm {'Start       {:initial          {:next 'Menu}}
          'Menu           {:adding           {:next 'Add :text {:id 1 :text "Add new to-do"}}
                           :reviewing        {:next 'Review :text {:id 2 :text "Review to-do list"}}
                           :focusing         {:next 'Focus :text {:id 3 :text "Focus on your priority to-do"}}
                           :about            {:next 'About :text {:id 4 :text "Read about AutoFocus"}}
                           :help             {:next 'Help :text {:id 5 :text "See the help section"}}
                           ;; TODO: confirm correct logic to export and import this menu-text option
                           :quitting         {:next 'Quit :text {:id "q" :text "Quit application"}}}
          'Add            {:done             {:next 'Menu}}
          'Review         {:done             {:next 'Menu}}
          'Focus          {:no-work-left     {:next 'Menu}
                           :work-remains     {:next 'Dup}}
          'Dup            {:done             {:next 'Menu}} ;; question: Does priority item duplication need its own state? What are the trade-offs?
          'About          {:done             {:next 'Menu}}
          'Help           {:done             {:next 'Menu}}
          'Quit           {:yes-quit         {:next 'Exit} ;; question: Does quit confirmation need its own state? What are the trade-offs?
                           :no-quit          {:next 'Menu}  }
})

;; TODO: implement this stub
(defn focus-on-app
  "1. Takes in the entire app state containing:
   - The list itself
   - The index of the last-done item
   2. Determines if the list is focusable
   - If yes: It updates the last-done and completes the marked item closest to the end of the list
   - If no: It returns the app-state as-is
   Note: last-done is 'the most recently completed item index'"
  [{:keys [current-ui the-list t-time] :as input-app}]
  (println "The UI state is: " current-ui)
  (println "The list is: " (list/stringify-list-compact the-list))
  (println (str "There are " (count the-list) " items in the list."))
  (println "Current list time is: " t-time)
  (println "The type of 'input-app' is: " (type input-app))
  (println "Focusable: " (list/is-focusable-list? the-list)))

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

;; (focus-on-app initial-program-state)