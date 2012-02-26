(ns filewinder.core)

(defn file-name-match? [file1 file2]
  (= (.getName file1) (.getName file2)))

(defn match-file [file dir]
  (filter (partial file-name-match? file) (file-seq dir)))

(defn matching-files [dir1 dir2]
  (for [file (file-seq dir1)]
    (filter (partial file-name-match? file) (file-seq dir2))))

(defn find-matches [source-dir target-dir]
  (zipmap (file-seq source-dir) (matching-files source-dir target-dir)))

(defn print-matches [matches]
  (for [[k vs] matches]
    (doall (println k) (for [v vs] (println (str "- " v))))))
