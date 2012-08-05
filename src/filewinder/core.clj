(ns filewinder.core
  (:use [clojure.java.io :only (file)])
  (:use [clojure.contrib.profile :only (profile prof)])
  (:gen-class))

(defn is-dir? [file]
  (.isDirectory file))

(defn files-in [file-seq]
  (remove is-dir? file-seq))

(defn file-name-match? [file1 file2]
  (= (.getName file1) (.getName file2)))

(defn match-file-name [file file-seq]
  (filter (partial file-name-match? file) file-seq))

(defn match-file-names [file-seq-1 file-seq-2]
  (for [file file-seq-1]
    (match-file-name file file-seq-2)))

(defn find-matching-file-names [source-file-seq target-file-seq]
  (zipmap source-file-seq (match-file-names source-file-seq target-file-seq)))

(defn filter-no-match [matches]
  (select-keys matches (for [[k v] matches :when (not-empty v)] k)))

(defn print-matches [matches]
  (doseq [[k vs] matches :when (not-empty vs)]
    (println (str k))
    (doseq [v vs]
      (println (str "- " v))))
  (flush))

(defn -main[source-dir-path target-dir-path]
  (print-matches (find-matching-file-names (files-in (file-seq (file source-dir-path))) (files-in (file-seq (file target-dir-path))))))

