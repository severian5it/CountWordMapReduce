import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.hadoop.mapreduce.Reducer;

// Frequency Reducer extending reducer class
public class FrequencyReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    
  // declaring totalCount that will bear total number of letter for the process
  double totalCount;
  
  //overriding set up method to initialite a cluster configuration, to retrieve the total counter  
  @Override
  public void setup(Context context) throws IOException, InterruptedException{
    // getting configuration
    Configuration conf = context.getConfiguration();
    // initialiating cluster
    Cluster cluster = new Cluster(conf);
    // getting current job settings
    org.apache.hadoop.mapreduce.Job currentJob = cluster.getJob(context.getJobID());
    // get MAPPER_RECORD_COUNTER value
    totalCount = currentJob.getCounters().findCounter(LetterMapper.MapperCounter.MAPPER_RECORD_COUNTER).getValue();  
  }

  public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
    // initialiating letterCount
    double letterCount = 0.0;
    double frequency;
    System.out.println(" In Reducer now!");
    for (DoubleWritable value : values) {
      // incrementing letterCount
      letterCount += value.get();
    }
    // calculating frequency using totalCount
    frequency = letterCount/totalCount;
    // writing Frequency in the reducer output
    context.write(key, new DoubleWritable(frequency));
  }
}