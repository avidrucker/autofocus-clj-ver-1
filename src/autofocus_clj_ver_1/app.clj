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