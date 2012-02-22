(ns filewinder.core)

(def source-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target"))

(defn file-name-match? [file1 file2]
  (= (.getName file1) (.getName file2)))

(def matches (for [source-file (file-seq source-dir)]
               (filter (partial file-name-match? source-file) (file-seq target-dir))))
(zipmap (file-seq source-dir) matches)
