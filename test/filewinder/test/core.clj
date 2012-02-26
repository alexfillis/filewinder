(ns filewinder.test.core
  (:use [filewinder.core])
  (:use [clojure.test]))

(def file1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json"))
(def sfile2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/project/build.xml"))
(def file2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/tmp.json"))
(def file3 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/one/test.txt"))
(def source-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target"))

(deftest should-return-true-for-two-files-with-same-names
  (is (file-name-match? file1 file2)))

(deftest should-return-false-for-two-files-with-different-names
  (is (not (file-name-match? file1 file3))))

(deftest file1-should-match-once-in-target-dir
  (is (= 1 (count (match-file file1 target-dir)))))

(deftest sfile2-should-not-match-any-file-in-target-dir
  (is (empty? (match-file sfile2 target-dir))))
