(ns autofocus-clj-ver-1.io)
;; [clojure.pprint :as p]

;; question: is this effectful, or side-effecting?
;; (defn print-tight [stuff-to-print]
;;   (binding [p/*print-right-margin* 30]
;;     (p/pprint stuff-to-print)))

;; (defn print-vertically [stuff-to-print]
;;   (concat "" (map #(str % "\n") stuff-to-print)))