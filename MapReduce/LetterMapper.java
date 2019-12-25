import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LetterMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    
    // Static Counter will count the number of letter going through the reducers in total.
    static enum MapperCounter {
        MAPPER_RECORD_COUNTER
    }

    public void map(LongWritable key, Text value, Context context)
                    throws IOException, InterruptedException {
        
        //lowercase of the string
        String s = value.toString().toLowerCase(); 
        
        // going through all the words composing text
        for (String word : s.split("\\W+")) {
          // going through all the letters composing a word
          for (int i = 0; i < word.length(); ++i) {
            char character = word.charAt(i);
            // using isLetter function to process letter
            if (isLetter(character)) {
              // incrementing counter  
              context.getCounter(MapperCounter.MAPPER_RECORD_COUNTER).increment(1);
              // send letter to reducer
              context.write(new Text(String.valueOf(character)), new DoubleWritable(1.0));
            }
          }        
        }
}
    // utility class returning TRUE for lettes
    private boolean isLetter(char character) {
        return (65 <= character && character <= 90)
                || (97 <= character && character <= 122);
    }
}