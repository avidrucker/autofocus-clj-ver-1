(ns autofocus-clj-ver-1.app
  (:require
   ;; [autofocus-clj-ver-1.io :as io]
   ;; TODO: remove this TEMP reference to util namespace
   [autofocus-clj-ver-1.list :as list]
   [autofocus-clj-ver-1.util :as util]))

;; TODO: implement this stub
(defn focus-on-app
  "1. Takes in the entire app state containing:
   - The list itself
   - The index of the last-done item
   2. Determines if the list is focusable
   - If yes: It updates the last-done and completes the marked item closest to the end of the list
   - If no: It returns the app-state as-is
   Note: last-done is 'the most recently completed item index'"
  [{:keys [list last-done]} input-app]
  (println "The list is: " (util/stringify-list-compact list))
  (println "Last done is: " last-done)
  (println "The type of 'input-app' is " (type input-app))
  (println "Focusable: " (list/is-focusable-list? list)))

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