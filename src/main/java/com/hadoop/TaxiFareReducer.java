package com.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class TaxiFareReducer extends Reducer<FloatWritable, FloatWritable, FloatWritable, FloatWritable> {
    public void reducer(FloatWritable key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
        float sum = 0; //sum of fare
        int count = 0;

        List<FloatWritable> valueList = new ArrayList<>();
for (FloatWritable val : values) {
    valueList.add(val);
}

for (int i = 0; i < valueList.size(); i++) {
    FloatWritable val = valueList.get(i);
    sum += val.get();
    count++;
}

        if (count > 0) {
            context.write(key, new FloatWritable(sum / count));
        }
    }
}
