(ns autofocus-clj-ver-1.examples)

;; TODO: convert these examples to EDN
;; question: Would this be a good idea, why/why not?

(do
  (def example-item
    {:status :clean
     :text "Wash the dishes"})

  (def example-empty-list [])

  (def example-list-one-item
    [{:status :clean
      :text "a"}])

  (def example-list-all-clean
    [{:status :clean
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-first-marked
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-first-completed
    [{:status :done
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-first-completed-second-marked
    [{:status :done
      :text "a"}
     {:status :marked
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-three-items-first-two-marked
    [{:status :marked
      :text "a"}
     {:status :marked
      :text "b"}
     {:status :clean
      :text "c"}])

  (def example-list-three-items-all-marked
    [{:status :marked
      :text "a"}
     {:status :marked
      :text "b"}
     {:status :marked
      :text "c"}])

  (def example-list-three-items-all-completed
    [{:status :done
      :text "a"}
     {:status :done
      :text "b"}
     {:status :done
      :text "c"}])

  (def example-list-three-items-first-and-last-marked
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :marked
      :text "c"}])

  (def example-list-three-items-first-marked-last-completed
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}
     {:status :done
      :text "c"}])

  (def example-list-two-items-first-completed
    [{:status :done
      :text "a"}
     {:status :clean
      :text "b"}])

  (def example-list-two-items-first-marked
    [{:status :marked
      :text "a"}
     {:status :clean
      :text "b"}])

  (def example-list-two-items-first-completed-second-marked
    [{:status :done
      :text "a"}
     {:status :marked
      :text "b"}])

  (def quick-three
    ["a" "b" "c"])

  (def long-e2e-list
    ["Email" "In-Tray" "Voicemail" "Project X Report" "Tidy Desk"
     "Call Dissatisfied Customer" "Make Dental Appointment"
     "File Invoices" "Discuss Project Y with Bob" "Back Up"])
  ;; (def regular-three
  ;;   ["Write report" "Check email" "Tidy desk"])

  ;; TODO: use this for small E2E tests [micro] [mini] [tiny]
  ;; (def fruit
  ;;   '("apple" "banana" "cherry"))
  )
