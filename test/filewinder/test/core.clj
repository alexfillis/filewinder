(ns filewinder.test.core
  (:use [filewinder.core])
  (:use [clojure.test]))

(def file1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json"))
(def file2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/tmp.json"))
(def file3 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/one/test.txt"))

(deftest should-return-true-for-two-files-with-same-names
  (is (file-name-match? file1 file2)))

(deftest should-return-false-for-two-files-with-different-names
  (is (not (file-name-match? file1 file3))))
