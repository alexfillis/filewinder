(ns filewinder.core)

(def source-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/source"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder/test-data/target"))

(defn file-name-match? [file1 file2]
  (= (.getName file1) (.getName file2)))

(defn match-files [dir1 dir2]
  (for [file (file-seq dir1)]
    (filter (partial file-name-match? file) (file-seq dir2))))

(zipmap (file-seq source-dir) matches)
