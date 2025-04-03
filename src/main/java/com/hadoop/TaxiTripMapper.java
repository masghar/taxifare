package com.hadoop;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TaxiTripMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    
    private Text tripDistance = new Text();
    private DoubleWritable fareAmount = new DoubleWritable();

    public void print(){
    System.out.println("hello world");   
    }
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Split the CSV line into columns (assuming the CSV is comma-separated)
        String[] columns = value.toString().split(",");

        try {
            // Extract the trip_distance and fare_amount (assuming these are in columns 10 and 15)
            String tripDistanceValue = columns[9].trim(); // Column index for trip_distance
            String fareAmountValue = columns[13].trim();  // Column index for fare_amount

            // If the values are not empty and valid numbers, emit the pair
            if (!tripDistanceValue.isEmpty() && !fareAmountValue.isEmpty()) {
                tripDistance.set(tripDistanceValue);
                fareAmount.set(Double.parseDouble(fareAmountValue)); // Convert fare_amount to double
                context.write(tripDistance, fareAmount); // Emit the pair
            }
        } catch (Exception e) {
            // In case of any errors, just skip this record
            System.err.println("Error processing line: " + value.toString());
        }
    }
}
