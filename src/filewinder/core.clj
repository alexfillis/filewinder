(ns filewinder.core
  (:gen-class))

(defn file-name-match? [file1 file2]
  (= (.getName file1) (.getName file2)))

(defn match-file [file dir]
  (filter (partial file-name-match? file) (file-seq dir)))

(defn match-files [dir1 dir2]
  (for [file (file-seq dir1)]
    (match-file file dir2)))

(defn find-matches [source-dir target-dir]
  (zipmap (file-seq source-dir) (match-files source-dir target-dir)))

(defn filter-no-match [matches]
  (select-keys matches (for [[k v] matches :when (not-empty v)] k)))

(defn print-matches [matches]
  (doseq [[k vs] matches :when (not-empty vs)]
    (println (str k))
    (doseq [v vs]
      (println (str "- " v))))
  (flush))

(defn -main[source-dir target-dir]
  (print-matches (find-matches (java.io.File. source-dir) (java.io.File. target-dir))))

