(ns filewinder.test.core
  (:use [filewinder.core])
  (:use [clojure.test]))

(def sfile1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json"))
(def sfile2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/project/build.xml"))
(def sfile3 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/test.txt"))
(def tfile1 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/tmp.json"))
(def tfile2 (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target/one/test.txt"))
(def source-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target"))

(deftest should-return-true-for-two-files-with-same-names
  (is (file-name-match? sfile1 tfile1)))

(deftest should-return-false-for-two-files-with-different-names
  (is (not (file-name-match? sfile1 tfile2))))

(deftest sfile3-should-match-once-in-target-dir
  (is (= 1 (count (match-file sfile3 target-dir)))))

(deftest sfile1-should-match-twice-in-target-dir
  (is (= 2 (count (match-file sfile1 target-dir)))))

(deftest sfile2-should-not-match-any-file-in-target-dir
  (is (empty? (match-file sfile2 target-dir))))

(deftest source-dir-should-match-three-files-in-target-dir
  (let [matches (match-files source-dir target-dir)]
    (is (= 6 (count matches)))
    (is (= 3 (count (remove empty? matches))))))

(deftest should-return-map-with-source-dir-as-key-matches-as-values
  (let [matches (find-matches source-dir target-dir)]
    (is (= 6 (count matches)))
    (is (empty? (get (find-matches source-dir target-dir) (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source"))))
    (is (empty? (get matches (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/project"))))
    (is (empty? (get matches (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/project/build.xml"))))
    (is (= 1 (count (get matches (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/test.txt")))))
    (is (= 2 (count (get matches (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json")))))
    (is (= 1 (count (get matches (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.xml")))))))
