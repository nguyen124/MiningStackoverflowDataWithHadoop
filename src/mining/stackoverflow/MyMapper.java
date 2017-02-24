package mining.stackoverflow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import mining.stackoverflow.ServerFault;
import mining.stackoverflow.User;

/**
 *
 * @author root
 */
public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private static List<User> users;

	private MyMapper() {
		if (users == null) {
			try {
				users = ServerFault.readUsers(HadoopXML.userPath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void map(LongWritable key, Text value1, Context context)

	throws IOException, InterruptedException {
		String xmlString = value1.toString();
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xmlString);
		try {
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> rows = root.getChildren("row");
			for (Element row : rows) {
				String ownerId = row.getAttributeValue("OwnerUserId");
				String postTypeId = row.getAttributeValue("PostTypeId");
				String score = row.getAttributeValue("Score");
				User user = ServerFault.findUser(users, Long.parseLong(ownerId));
				if (user != null) {
					context.write(
							new Text(user.getId() + "," + user.getDisplayName() + "," + postTypeId),
							new IntWritable(Integer.parseInt(score)));
				}
			}
		} catch (JDOMException ex) {
			Logger.getLogger(MyMapper.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MyMapper.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
