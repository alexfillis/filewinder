(ns filewinder.core)
(def source-file (java.io.File. "/home/alex/dev/play/clojure/filewinder/src/core.clj"))
(def target-dir (java.io.File. "/home/alex/dev/play/clojure/filewinder"))
(defn file-name-match? [file]
  (= (.getName file) (.getName source-file)))
(filter file-name-match? (file-seq target-dir))
