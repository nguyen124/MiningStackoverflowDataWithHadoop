 I) Problem Statement:

Analyzing social networks on StackOverflow Web forums. People who post lots of questions vs. lots of answers. As a starting point, they would like the following two lists of users:
The top 10 users by total score on questions asked
The top 10 users by total score on answers posted In both cases, they are only interested in posts from the past 6 months. They are interested in usernames, not user ids.
The data is from Stack Overflow (technically from their affiliate site Server Fault). The full, much-larger, data set here: https://archive.org/details/stackexchange. The files that you will need are stackoverflow.com-Posts.7z and stackoverflow.com-Users.7z That download is much too big to fit into an e-mail; it's 9.2GB compressed.
II) Solution with Hadoop: To understand this more easily please read my code from MiningStackoverflowData repository
Read all users into the memory to and read post one by one 
compare the owner id and user id then write the post's owner id, name and score into text file. 
From the text file, hadoop will map and reduce into key values with the key is combination from userid and name, 
the value is the total of score of the posts which have them same owner.
AFter hadoop spits out the processed file, we again read back into memory and sort these values then give out the top scores. 
I dind't write the code to get all top scores because it will be similar to the first solution. 
I just try to apply Hadoop Map Reduce framework into processing original xml files. 
I tested my code on the distributed system biggreen.marshall.edu and it mapped correctly the data. 
In the folder XmlFiles contains original xml files. 
The input folder contains the intermediate file which has list of posts and 
the output folder contains the result from mapping and reducing of hadoop.
