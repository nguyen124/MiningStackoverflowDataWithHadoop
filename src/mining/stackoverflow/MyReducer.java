package mining.stackoverflow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;

import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	int maxQuestionScore = Integer.MIN_VALUE;
	int maxAnswerScore = Integer.MIN_VALUE;
	Text maxQuestionKey = new Text();
	Text maxAnswerKey = new Text();
	Map<Text, IntWritable> topQuestion = new HashMap<Text, IntWritable>();
	Map<Text, IntWritable> topAnswer = new HashMap<Text, IntWritable>();

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		// int maxValue = Integer.MIN_VALUE;
		// int minValue = Integer.MAX_VALUE;
		// while (values.hasNext()) {
		// int tempValue = values.next().get();
		// maxValue = Math.max(maxValue, tempValue);
		// minValue = Math.min(minValue, tempValue);
		// }
		// output.collect(new Text(key+"-Max"), new IntWritable(maxValue));
		// output.collect(new Text(key+"-Min"), new IntWritable(minValue));
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		context.write(key, new IntWritable(sum));
	}

}
