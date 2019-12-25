#removing existing books folder
hdfs dfs -rm -R /books/*

#creating folder in HDFS as in source
for folder in $@/*
 do 
   hdfs dfs -mkdir /books/`basename $folder`
 done

#copying files on HDFS
for folder in $@/*
 do 
   hdfs dfs -put $folder/* /books/`basename $folder`/
 done

#executing Hadoop map reduce processes
for folder in `hdfs dfs -ls /books | awk '{print $8}'`
 do 
    hdfs dfs -rm -R $folder/output 
    hadoop jar LetterFrequency.jar LetterFrequency $folder $folder/output
 done

#merging hadoop output back to source
for folder in `hdfs dfs -ls /books/*/ | grep "^d"  | awk '{print $8}'`
  do 
    dirfolder=`dirname $folder`
    hadoop dfs -getmerge $folder $@/`basename $dirfolder`out.txt
  done 