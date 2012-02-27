(ns filewinder.core
  (:use [clojure.pprint :only (pprint)])
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

(defn print-matches [matches]
  (for [[k vs] matches]
    (doall (println k) (for [v vs] (println (str "- " v))))))

(defn -main[source-dir target-dir]
  (println (pprint (find-matches (java.io.File. source-dir) (java.io.File. target-dir)))))

