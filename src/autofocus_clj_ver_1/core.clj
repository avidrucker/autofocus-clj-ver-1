
(ns autofocus-clj-ver-1.core
  (:gen-class)
  (:require
   [autofocus-clj-ver-1.cli :as cli]
))

(defn -main
  "runs the entire AutoFocus program"
  []
  ;; MAIN PROGRAM GOES HERE
  ;; (io/-main)
  ;; (demos/-main)
  (cli/run-cli-app)
  )

;; (-main)