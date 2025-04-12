package com.hadoop;

/*Driver Class fro MapReduce */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TaxiFareDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Average Fare per Trip Distance");

        job.setJarByClass(TaxiFareDriver.class);
        job.setMapperClass(TaxiFareMapper.class);
        job.setReducerClass(TaxiFareReducer.class);

        job.setOutputKeyClass(FloatWritable.class);
        job.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));  // Input CSV file of the dataset
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output path after processing
        // Delete the output path if it already exists              

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
