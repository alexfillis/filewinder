(ns filewinder.test.core
  (:use [filewinder.core])
  (:use [clojure.test])
  (:use [clojure.java.io :only (file)]))

(def sfile1 (file "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.json"))
(def sfile2 (file "/home/alex/dev/play/clojure/filewinder/test-data/source/project/build.xml"))
(def sfile3 (file "/home/alex/dev/play/clojure/filewinder/test-data/source/test.txt"))
(def sfile4 (file "/home/alex/dev/play/clojure/filewinder/test-data/source/tmp.xml"))
(def sfile5 (file "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def sfile6 (file "/home/alex/dev/play/clojure/filewinder/test-data/source/project"))
(def tfile1 (file "/home/alex/dev/play/clojure/filewinder/test-data/target/tmp.json"))
(def tfile2 (file "/home/alex/dev/play/clojure/filewinder/test-data/target/one/test.txt"))
(def tfile3 (file "/home/alex/dev/play/clojure/filewinder/test-data/target/one/tmp.json"))
(def tfile4 (file "/home/alex/dev/play/clojure/filewinder/test-data/target/one/tmp.xml"))
(def source-dir (file "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def target-dir (file "/home/alex/dev/play/clojure/filewinder/test-data/target"))

(def matches {sfile1 (seq '(tfile1 tfile3)),
                 sfile2 '(),
                 sfile3 (seq '(tfile2)),
                 sfile4 (seq '(tfile4)),
                 sfile5 '(),
                 sfile6 '()})

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
    (is (empty? (get (find-matches source-dir  target-dir) sfile5)))
    (is (empty? (get matches sfile6)))
    (is (empty? (get matches sfile2)))
    (is (= 1 (count (get matches sfile3))))
    (is (= 2 (count (get matches sfile1))))
    (is (= 1 (count (get matches sfile4))))))

(deftest should-remove-entries-with-no-matches
  (let [filtered-matches (filter-no-match matches)]
    (is (= 3 (count filtered-matches)))
    (is (= 1 (count (get filtered-matches sfile3))))
    (is (= 2 (count (get filtered-matches sfile1))))
    (is (= 1 (count (get filtered-matches sfile4))))))