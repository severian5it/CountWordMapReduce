import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class LetterPartitioner extends Partitioner<Text, DoubleWritable> {

  //overriding getpartition method 
  @Override
  public int getPartition(Text key, DoubleWritable value, int numReduceTasks) {
    
    String letter = key.toString();
    
    // sending vowel to a reducer
    if ("aeiou".indexOf(letter) < 0) {
      return 0;
    }
    else {
      // sending consonant to the second reducer  
      return 1;
    }
  }
}