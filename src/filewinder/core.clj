(ns filewinder.core)
(def source-file (java.io.File. "/home/alex/dev/play/clojure/filewinder/src/core.clj"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder"))
(defn file-name-match? [file1 file2]
  (= (.getName file1) (.getName file2)))
(filter (partial file-name-match? source-file) (file-seq target-dir))
