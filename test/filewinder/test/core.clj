(ns filewinder.test.core
  (:use [filewinder.core])
  (:use [clojure.test]))

(def file1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json"))
(def file2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/tmp.json"))

(deftest should-return-true-for-two-files-with-same-name
  (is (file-name-match? file1 file2)))
