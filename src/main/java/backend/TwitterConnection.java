package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author prash
 */
public class TwitterConnection {
    private static Map<String, String> keys;
    private static Twitter twitter;
    
    public TwitterConnection(HashMap<String, String> keys) {
        TwitterConnection.keys = keys;
        createConnection();
    }
    
    public static List<Status> searchTweets(String term, int count) throws TwitterException {
        List<Status> res = new ArrayList();
        int remainingTweets = count;
        Query query = new Query(term);
        
        try{
            while(remainingTweets > 0) {
                int queryCount =  remainingTweets > 50 ? 50 : remainingTweets;
                query.count(queryCount);
                QueryResult result = twitter.search(query);
                res.addAll(result.getTweets());
                if (result.hasNext()) {
                    query = result.nextQuery();
                }
                remainingTweets -= queryCount;
            }
        } catch(TwitterException e){}
        
        for (Status tweet : res) {
            System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        }
        
        return res;
    }

    public boolean createConnection() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(keys.get("ConsumerKey"));
        cb.setOAuthConsumerSecret(keys.get("ConsumerSecret"));
        cb.setOAuthAccessToken(keys.get("AccessToken"));
        cb.setOAuthAccessTokenSecret(keys.get("AccessTokenSecret"));
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        System.out.println("Connection established");
        
        return true;
    }
    
    public static Status getStatus(long tweetId) {
        try {
            return twitter.showStatus(tweetId);
        } catch(TwitterException e){}
        
        return null;
    }
    
}
