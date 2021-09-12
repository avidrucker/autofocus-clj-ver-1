(ns autofocus-clj-ver-1.public-test
  (:require [clojure.test :refer [deftest is testing]]
            [autofocus-clj-ver-1.core :as af]
            [autofocus-clj-ver-1.examples :as eg]
            [autofocus-clj-ver-1.public :as pub]))

;; EXTERNAL API

;; these tests will only test one function
(deftest unit-tests
  (testing "Focusing on a list"
    (is (= eg/example-list-three-items-first-marked-last-completed
           (pub/focus-on-list
            eg/example-list-three-items-first-and-last-marked))
        "Correctly marks the last item as done when list IS focusable")
    (is (= eg/example-list-three-items-all-completed
           (pub/focus-on-list
            eg/example-list-three-items-all-completed))
        "Correctly does nothing when list is NOT focusable")
    (is (= eg/example-list-all-clean
           (pub/focus-on-list
            eg/example-list-all-clean))
        "Correctly does nothing when list is NOT focusable")))

(defn scaffold-integration-test-1
  "makes an empty list, adds one item to it,
   and returns the new list of size 1"
  []
  (pub/add-item-to-list
   [] ;; blank new empty list
   (af/create-new-item-from-text "a") ;; newly created to-do item
   ))

;; TODO: replace this with external facing API once it 
;;       is built
(defn scaffold-list-from-strings
  "temporary setup function to scaffold integration tests"
  [input-strings]
  (let [item-list
        (map af/create-new-item-from-text input-strings)]
    (vec (flatten (into []
                        (map #(pub/add-item-to-list [] %)
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
         (af/stringify-list-compact
          (pub/focus-on-list
           (pub/review-list
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

  ;; ref: https://stackoverflow.com/questions/7658981/how-to-reload-a-clojure-file-in-repl
  (use 'autofocus-clj-ver-1.public-test :reload)

  (testing "long flow integration test"
    (is (= "[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                af/stringify-list-compact)))

    (is (= "[o] [ ] [o] [ ] [o] [ ] [ ] [ ] [ ] [ ]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                (#(pub/review-list % ["n" "y" "n" "y" "q"]))
                af/stringify-list-compact)))
    
    ;; this test requires 'lastDone' to function as desired
    (is (= "[o] [ ] [o] [ ] [x] [ ] [ ] [ ] [ ] [o]"
           (->> eg/long-flow-list
                scaffold-list-from-strings
                (#(pub/review-list % ["n" "y" "n" "y" "q"]))
                pub/focus-on-list
                af/stringify-list-compact)))
    
    ;; "[o] [ ] [o] [ ] [x] [ ] [ ] [ ] [ ] [o]"
    ;; (#(pub/review-list % ["n", "n", "n", "n", "y"]))
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

;; note: e2e tests, in order to be e2e tests, must touch either
;;       command line IO or web interface IO
(deftest end-to-end-tests
  ;; code goes here
  )