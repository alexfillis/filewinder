(ns filewinder.test.core
  (:use [filewinder.core])
  (:use [clojure.test]))

(def sfile1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json"))
(def sfile2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/project/build.xml"))
(def tfile1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/tmp.json"))
(def tfile2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/one/test.txt"))
(def source-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target"))

(deftest should-return-true-for-two-files-with-same-names
  (is (file-name-match? sfile1 tfile1)))

(deftest should-return-false-for-two-files-with-different-names
  (is (not (file-name-match? sfile1 tfile2))))

(deftest sfile1-should-match-twice-in-target-dir
  (is (= 2 (count (match-file sfile1 target-dir)))))

(deftest sfile2-should-not-match-any-file-in-target-dir
  (is (empty? (match-file sfile2 target-dir))))

(deftest source-dir-should-match-three-files-in-target-dir
  (is (= 6 (count (match-files source-dir target-dir))))
  (is (= 3 (count (remove empty? (match-files source-dir target-dir))))))

