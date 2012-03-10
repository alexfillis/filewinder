(ns filewinder.core
  (:use [clojure.java.io :only (file)])
  (:use [clojure.contrib.profile :only (profile prof)])
  (:gen-class))

(defn file-name-match? [file1 file2]
  (prof :file-name-match (= (.getName file1) (.getName file2))))

(defn match-file [file dir]
  (prof :match-file (filter (partial file-name-match? file) (file-seq dir))))

(defn match-files [dir1 dir2]
  (prof :match-files (for [file (file-seq dir1)]
    (match-file file dir2))))

(defn find-matches [source-dir target-dir]
  (prof :find-matches (zipmap (file-seq source-dir) (match-files source-dir target-dir))))

(defn filter-no-match [matches]
  (prof :filter-no-match (select-keys matches (for [[k v] matches :when (not-empty v)] k))))

(defn print-matches [matches]
  (prof :print-matches (doseq [[k vs] matches :when (not-empty vs)]
    (println (str k))
    (doseq [v vs]
      (println (str "- " v)))))
  (flush))

(defn -main[source-dir target-dir]
  (profile (print-matches (find-matches (file source-dir) (file target-dir)))))

