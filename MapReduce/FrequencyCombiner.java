import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FrequencyCombiner extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {
      // initialating letterCount
      double letterCount = 0.0;
      System.out.println(" In Combiner now!");
      for (DoubleWritable value : values) {
        // incrementing letterCount
        letterCount += value.get();
      }
      // send combiner output to reducer
      context.write(key, new DoubleWritable(letterCount));
    }
}