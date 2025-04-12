package com.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class TaxiFareMapper extends Mapper<LongWritable, Text, FloatWritable, FloatWritable> {


    //my csv file data is in index 4 and 10
    // 4th index is distance and 10th index is fare     
    // 4: trip_distance,
    // 10: fare_amount, 
    private static final int DISTANCE_INDEX = 4; // 
    private static final int FARE_INDEX = 10;
    private static final int MIN_EXPECTED_FIELDS = 11; // to check total number of fields.

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();

        if (line.isEmpty()) {
            return; // Skip blank lines
        }

        String[] fields = line.split(",");

        // Validate field length
        if (fields.length < MIN_EXPECTED_FIELDS) {
            System.err.println("row is invalid " + line);
            return;
        }

        try {
            float tripDistance = Float.parseFloat(fields[DISTANCE_INDEX]);
            float tripFare = Float.parseFloat(fields[FARE_INDEX]);

            // Write only valid (non-zero) distances
            if (tripDistance > 0.0f) {
                context.write(new FloatWritable(tripDistance), new FloatWritable(tripFare));
            }
        } catch (NumberFormatException nfe) {
            // Log malformed numeric data
            System.err.println("number foramt issue " + line);
        } catch (Exception e) {
            // Catch-all for unexpected errors (e.g., array issues)
            System.err.println("all other errors: " + line);
        }
    }
}
