import org.apache.hadoop.fs.Path;
//DoubleWritable class used in place of IntWritable, to take into account decimals
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LetterFrequency {
    public static void main(String[] args) throws Exception {
        
        if (args.length != 2) {
            System.err.println(args);
            System.err.println("Usage: LetterFrequency <input path> <output path>");
            System.exit(-1); }
        
        System.out.println("In Driver now!");
        
        Job job = Job.getInstance();
        job.setJarByClass(LetterFrequency.class);
        job.setJobName("LetterFrequency");
        
        // set number of reducers, change to 0 to control mapper output.
        job.setNumReduceTasks(2);   
        
        job.setMapperClass(LetterMapper.class);
        
        job.setCombinerClass(FrequencyCombiner.class);
        
        job.setPartitionerClass(LetterPartitioner.class);
        
        job.setReducerClass(FrequencyReducer.class);
        
        // output set to use DoubleWritable
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        
        //using paths provided as argument
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}