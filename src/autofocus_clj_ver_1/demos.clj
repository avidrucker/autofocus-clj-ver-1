(ns autofocus-clj-ver-1.demos)
;; [clojure.pprint :as p]

;; TODO: convert to an integration test
;; (defn demo1
;;   "prints out empty list, adds a new item, and then prints again"
;;   []
;;   (let [app-state1 {:current-list []}
;;         app-state2 (assoc app-state1
;;                           :current-list
;;                           (add-item-to-list
;;                            (app-state1 :current-list)
;;                            example-item))]

;;     (print-demo 1 app-state1 app-state2)))

;; (defn text-to-todo-item
;;   "converts text input to new to-do list item"
;;   [text-input]
;;   {:id "1234567890" ;; TODO: replace hardcoded non-unique id w/ incremental, unique
;;    :status :clean
;;    :text text-input
;;    :is-hidden false})

;; TODO: Convert to an integration test
;; TODO: implement automark of first markable item
;; (defn demo2
;;   "adds three items to list, prints, 
;;    automarks the first markable item, 
;;    and then prints again"
;;   []
;;   (let [app-state1 [] ;; {:current-list []}
;;         ;; Build fruit to-do items
;;         items-to-add (map text-to-todo-item fruit)
;;         ;; Note: Mapping here converts vector to list
;;         ;;       ... which makes vector/sequence only
;;         ;;       functions not behave (at all or as expected)
;;         ;; question: Is there a cleaner way to map over
;;         ;;     a data source (in this case the fruit list)
;;         ;;     without needing to flatten and vec back to
;;         ;;     the desired shape? (flat vector of hashmaps)  
;;         ;; Add fruit to-do items to to-do list
;;         app-state2 (vec (flatten (map
;;                                   #(add-item-to-list
;;                                     app-state1 %)
;;                                   items-to-add)))
;;         app-state3 (mark-first-item app-state2)]

;;     ;; TODO: replace printout with separate function
;;     (binding [p/*print-right-margin* 30]
;;       (println "start of demo2")
;;       (println "before (1):")
;;       (p/pprint (stringify-list app-state1))
;;       (println "after (2):")
;;       (p/pprint (stringify-list app-state2))
;;       (println "final (3):")
;;       (p/pprint (stringify-list app-state3))
;;       (println "end of demo2"))))

;; (defn print-demo [number before-state after-state]
;;   (binding [p/*print-right-margin* 30]
;;     (println (str "start of demo" number))
;;     (println "before:")
;;     (p/pprint (stringify-list (before-state :current-list)))
;;     (println "after:")
;;     (p/pprint (stringify-list (after-state :current-list)))
;;     (println (str "end of demo" number))))