(ns autofocus-clj-ver-1.list-test
  (:require [clojure.test :refer [deftest is testing]]
            [autofocus-clj-ver-1.examples :as eg]
            [autofocus-clj-ver-1.item :as item]
            [autofocus-clj-ver-1.list :as list]))

;; "inner" API

(deftest unit-tests-inner
  (testing "Rendering lists"
    (is
     (= "list is empty"
        (list/stringify-list []))
     "Rendering an empty list works as expected")
    (is
     (= " - [ ] Wash the dishes"
        ;; TODO: replace the following with an actual list
        (list/stringify-list [eg/example-item]))
     "Rendering a list with one item works as expected")
    (is
     (= " - [ ] a\n - [ ] b\n - [ ] c"
        (list/stringify-list eg/example-list-all-clean))
     "Rendering a list that has only 
      clean items works as expected")
    (is
     (= "[ ] [ ] [ ]"
        (list/stringify-list-compact eg/example-list-all-clean))
     "Compact-rendering a list with 
      three clean items works as expected"))

  (testing "Determine if certain status exists in a list"
    (is (= true (list/list-has-items-of-status
                 eg/example-list-all-clean
                 :clean)))
    (is (= false (list/list-has-items-of-status
                  eg/example-list-all-clean
                  :done)))
    (is (= false (list/list-has-items-of-status
                  eg/example-list-first-completed
                  :marked)))
    (is (= true (list/list-has-items-of-status
                 eg/example-list-first-completed-second-marked
                 :clean)))
    (is (= true (list/list-has-items-of-status
                 eg/example-list-first-completed-second-marked
                 :marked)))
    (is (= true (list/list-has-items-of-status
                 eg/example-list-first-completed-second-marked
                 :done))))

  (testing "Determine if certain status DOES NOT EXIST in a list"
    (is (= false (list/list-has-none-of-status
                  eg/example-list-all-clean
                  :clean)))
    (is (= true (list/list-has-none-of-status
                 eg/example-list-all-clean
                 :done)))
    (is (= true (list/list-has-none-of-status
                 eg/example-list-first-completed
                 :marked)))
    (is (= false (list/list-has-none-of-status
                  eg/example-list-first-completed-second-marked
                  :clean)))
    (is (= false (list/list-has-none-of-status
                  eg/example-list-first-completed-second-marked
                  :marked)))
    (is (= false (list/list-has-none-of-status
                  eg/example-list-first-completed-second-marked
                  :done))))

  (testing "Determining if a list is auto-markable"
    (is (= false
           (list/is-auto-markable-list?
            eg/example-empty-list)))
    (is (= true
           (list/is-auto-markable-list?
            eg/example-list-all-clean)))
    (is (= true
           (list/is-auto-markable-list?
            eg/example-list-first-completed)))
    (is (= false
           (list/is-auto-markable-list?
            eg/example-list-first-completed-second-marked))))

  (testing "Finding first markable item in a list"
    (is (= 0 (list/index-of-first-markable
              eg/example-list-all-clean)))
    (is (= 1 (list/index-of-first-markable
              eg/example-list-first-completed))))

  (testing "Marking first markable item in a list"
    (is (= "[o] [ ] [ ]"
           (list/stringify-list-compact
            (list/mark-first-markable
             eg/example-list-all-clean)))
        "Marking a list that has only clean 
         items works as expected")
    (is (= "[x] [o] [ ]"
           (list/stringify-list-compact
            (list/mark-first-markable
             eg/example-list-first-completed)))
        "Marking a list w/ one completed & two 
         clean items works as expected"))

  (testing "Generating review questions"
    (is (= "Do you want to do 'b' more than 'a'?"
           (list/generate-review-msg eg/example-list-first-marked 1))
        "Generates the correct review message."))

  (testing "Determining if a list is reviewable"
    ;; list of size 0
    (is (= false (list/is-reviewable-list? eg/example-empty-list))
        "Correctly determines list NOT reviewable")
    ;; lists of size 1
    (is (= false (list/is-reviewable-list? eg/example-list-one-item))
        "Correctly determines list NOT reviewable")
    ;; lists of size 2
    (is (= false (list/is-reviewable-list?
                  eg/example-list-two-items-first-completed))
        "Correctly determines list NOT reviewable")
    (is (= true (list/is-reviewable-list?
                 eg/example-list-two-items-first-marked))
        "Correctly determines that list IS reviewable")

    ;; lists of size 3
    ;; yes: example-list-first-marked
    ;; yes: example-list-first-completed-second-marked
    ;; no: example-list-three-items-all-completed
    ;; no: example-list-three-items-first-and-last-marked
    (is (= true (list/is-reviewable-list?
                 eg/example-list-first-marked))
        "Correctly determines that list IS reviewable")
    (is (= true (list/is-reviewable-list?
                 eg/example-list-first-completed-second-marked))
        "Correctly determines that list IS reviewable")
    (is (= false (list/is-reviewable-list?
                  eg/example-list-three-items-all-completed))
        "Correctly determines list NOT reviewable")
    (is (= false (list/is-reviewable-list?
                  eg/example-list-three-items-first-and-last-marked))
        "Correctly determines list NOT reviewable"))

  (testing "Marking the last marked item as done"
    (is (= eg/example-list-three-items-first-marked-last-completed
           (list/mark-closest-to-end-marked-item-done
            eg/example-list-three-items-first-and-last-marked))
        "Correctly marks the last item as done"))

  (testing "Selecting the first index to start reviewing"
    (is (= 1 (list/get-first-reviewable-index
              eg/example-list-first-marked)))
    (is (= 2 (list/get-first-reviewable-index
              eg/example-list-first-completed-second-marked))))

  ;; TODO: convert the example below to a test
  ;; EXAMPLE:
  ;; (apply-answers
  ;;  example-list-first-marked '("y" "y"))
  ;; modifications => {1 "y", 2 "y"}
  ;; applied-answers => [{:status :marked, :text "a"}
  ;;                     {:status :marked, :text "b"} 
  ;;                     {:status :marked, :text "c"}]
  (testing "Applying answers to a list"
    (is (= eg/example-list-three-items-all-marked
           (list/apply-answers
            eg/example-list-first-marked '("y" "y")))
        "Correctly modifies a list")
    (is (= eg/example-list-three-items-first-and-last-marked
           (list/apply-answers
            eg/example-list-first-marked '("n" "y")))
        "Correctly modifies a list")
    (is (= eg/example-list-three-items-first-two-marked
           (list/apply-answers
            eg/example-list-first-marked '("y" "q")))
        "Correctly modifies a list")))

;; "middle API"

;; these tests will only test one function
(deftest unit-tests-middle
  (testing "Focusing on a list"
    (is (= eg/example-list-three-items-first-marked-last-completed
           (list/focus-on-list
            eg/example-list-three-items-first-and-last-marked))
        "Correctly marks the last item as done when list IS focusable")
    (is (= eg/example-list-three-items-all-completed
           (list/focus-on-list
            eg/example-list-three-items-all-completed))
        "Correctly does nothing when list is NOT focusable")
    (is (= eg/example-list-all-clean
           (list/focus-on-list
            eg/example-list-all-clean))
        "Correctly does nothing when list is NOT focusable")))

(defn scaffold-integration-test-1
  "makes an empty list, adds one item to it,
   and returns the new list of size 1"
  []
  (list/add-item-to-list
   [] ;; blank new empty list
   (item/create-new-item-from-text "a") ;; newly created to-do item
   ))

;; TODO: replace this with external facing API once it 
;;       is built
(defn scaffold-list-from-strings
  "temporary setup function to scaffold integration tests"
  [input-strings]
  (let [item-list
        (map item/create-new-item-from-text input-strings)]
    (vec (flatten (into []
                        (map #(list/add-item-to-list [] %)
                             item-list))))))

;; these tests will test a combination of public and private APIs
(deftest integration-tests
  ;; question: How are integration/E2E tests set up in
  ;;  an effective manner in Clojure?
  (testing "adding items to a list"
    (is (=
         (scaffold-integration-test-1)
         eg/example-list-one-item)
        "works as expected"))

  (testing "add items, review items, and focus on an item"
    (is (=
         "[o] [o] [x]"
         (list/stringify-list-compact
          (list/focus-on-list
           (list/review-list
            (scaffold-list-from-strings
             eg/quick-three) ["y" "y"]))))
        "works as expected"))

  ;; reference: http://markforster.squarespace.com/blog/2015/5/21/the-final-version-perfected-fvp.html

  ;; TODO: implement this test stub
  ;; first review: ["n", "y", "n", "y", "q"]
  ;; => "[o] [ ] [o] [ ] [o] [ ] [ ] [ ] [ ] [ ]"
  ;; second review: ["n", "n", "n", "n", "y"]
  ;; => "[o] [ ] [o] [ ] [x] [ ] [ ] [ ] [ ] [o]"
  ;; third focus:
  ;; => "[o] [ ] [x] [ ] [x] [ ] [ ] [ ] [ ] [x]"
  ;; third review: ["n", "n", "y", "n", "y"]
  ;; => "[o] [ ] [x] [ ] [x] [ ] [o] [ ] [o] [x]"
  ;; fourth and fifth focuses:
  ;; => "[o] [ ] [x] [ ] [x] [ ] [x] [ ] [x] [x]"

  ;; TIL: "reeval entire file clojure hot reload"
  ;;      "How to reload a clojure file in REPL"
  ;; ref: https://stackoverflow.com/questions/7658981/how-to-reload-a-clojure-file-in-repl
  ;; (use 'autofocus-clj-ver-1.list-test :reload)

  (testing "long flow integration test"
    (is (= "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                list/stringify-list-compact)))

    (is (= "[o] [ ] [o] [ ] [o] [ ] [ ] [ ] [ ] [ ]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                ;; TIL: "clojure anonymous function in thread last"
                ;;      "threading macro with anonymous functions"
                ;; https://stackoverflow.com/questions/10740265/threading-macro-with-anonymous-functions
                (#(list/review-list % ["n" "y" "n" "y" "q"]))
                list/stringify-list-compact)))

    (is (= "[o] [ ] [o] [ ] [x] [ ] [ ] [ ] [ ] [ ]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                (#(list/review-list % ["n" "y" "n" "y" "q"]))
                list/focus-on-list
                list/stringify-list-compact)))

    ;; TODO: implement last-done to remove blocker to implementing this test
    ;; this test requires 'last-done' to function as desired
    (is (= "[o] [ ] [o] [ ] [x] [ ] [ ] [ ] [ ] [o]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                (#(list/review-list % ["n" "y" "n" "y" "q"]))
                list/focus-on-list
                (#(list/review-list % ["n", "n", "n", "n", "y"]))
                list/stringify-list-compact)))
    )
  
  ;; FIRST REVIEW
  ;; "Now ask yourself 'What do I want to do more than Email?'
  ;; You decide you want to do Voicemail more than Email.
  ;; Put a dot in front of it.
  ;; Now ask yourself 'What do I want to do more than Voicemail?'
  ;; You decide you want to tidy your desk."
  ;; review items, saying yes only for 3rd & 5th items
  ;; FIRST FOCUS
  ;; Do the "Tidy Desk" task (last marked item / CMWTD)
  ;; SECOND REVIEW
  ;; "Now start again from Tidy Desk (i.e. the last task you did).
  ;; and ask yourself 'What do I want to do more than Voicemail?'
  ;; The only task you want to do more than Voicemail is Back Up."
  ;; review items, saying yes only to last item (in this review it will be the 5th)
  ;; SECOND FOCUS
  ;; "Do it." (Back Up)
  ;; THIRD FOCUS
  ;; "There are no further tasks beyond Back Up, so there is no
  ;; need to check whether you want to do any tasks more than
  ;; you want to do Voicemail. You just do it."
  ;; THIRD REVIEW
  ;; "You already know that you want to do Email more than In-tray, so you start
  ;; scanning from the first task after the task you have just done (Voicemail)."
  ;; "You decide you want to do Make Dental Appointment"
  ;; FOURTH AND FIFTH FOCUSES:
  ;; As this is the last task on the list you do it immediately,
  ;; and then do Make Dental Appointment immediately too.
  )

;; "outter" API

;; note: e2e tests, in order to be e2e tests, must touch either
;;       command line IO or web interface IO
(deftest end-to-end-tests
  ;; code goes here
  )