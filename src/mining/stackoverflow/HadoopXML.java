package mining.stackoverflow;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class HadoopXML {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.err.println(
					"Usage: XmlFiles <UserFile> HadoopXML <input path> <output path>");
			System.exit(-1);
		}
		userPath = args[0];
		runJob(args[1], args[2]);
	}

	public static String userPath;

	public static void runJob(String input, String output) throws IOException {

		Configuration conf = new Configuration();
		conf.set("xmlinput.start", "<posts>");
		conf.set("xmlinput.end", "</posts>");
		conf.set("io.serializations",
				"org.apache.hadoop.io.serializer.JavaSerialization,org.apache.hadoop.io.serializer.WritableSerialization");
		Job job = new Job(conf, "Xml Parsing");

		FileInputFormat.setInputPaths(job, input);
		job.setJarByClass(HadoopXML.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setNumReduceTasks(2);
		job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		Path outPath = new Path(output);
		FileOutputFormat.setOutputPath(job, outPath);
		FileSystem dfs = FileSystem.get(outPath.toUri(), conf);
		if (dfs.exists(outPath)) {
			dfs.delete(outPath, true);
		}

		try {

			job.waitForCompletion(true);

		} catch (InterruptedException ex) {
			Logger.getLogger(HadoopXML.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(HadoopXML.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
// ^^ MaxTemperature
