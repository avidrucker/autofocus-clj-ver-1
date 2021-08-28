(ns autofocus-clj-ver-1.io)
;; [clojure.pprint :as p]

;; question: is this effectful, or side-effecting?
;; (defn print-tight [stuff-to-print]
;;   (binding [p/*print-right-margin* 30]
;;     (p/pprint stuff-to-print)))

;; (defn print-vertically [stuff-to-print]
;;   (concat "" (map #(str % "\n") stuff-to-print)))

;; initial source template:
;; https://github.com/ggandor/eliza-in-clojure/blob/master/src/eliza_basic.clj
(def exit-opt " (type Q to quit)")
(def question-x "What is your name?")
(def response-x "Oh, your name is ")

(defn isAlphaOnly? [in]
  (=
   (count (clojure.string/trim in))
   (count (filter (fn [x] (Character/isLetter x)) in))))

(defn valid-input?
  [in]
  (and
   (not (number? in))
   (isAlphaOnly? (str in))
   (not= "" (clojure.string/trim (str in)))))

(defn reply
  [in]
  (if (valid-input? in)
    (println (str response-x (clojure.string/trim in) "!"))
    (println "That's not valid input, try again.")))

(defn not-quitting? [in]
  (and (not= in "q") (not= in "Q")))

(defn user-io
  "Respond to user input."
  [prompt-msg]
  (println prompt-msg)
  (flush)
  (let [input (read-line)]
    (when (not-quitting? (str input))
      (reply input)
      (recur prompt-msg))))

(defn -main []
  (user-io (str question-x exit-opt))
  (println "Bye for now!"))

;; (-main)