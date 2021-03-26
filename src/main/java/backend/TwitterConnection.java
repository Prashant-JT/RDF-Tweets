package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.User;
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
    private final Map<String, String> keys;
    
    public TwitterConnection(HashMap<String, String> keys) throws TwitterException {
        this.keys = keys;
        this.createConnection();
    }
    
    public List<Status> searchTweets(String term, int count) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        List<Status> tweets = null;
        
        Query query = new Query(term);
        while(count > 0) {
            QueryResult result = twitter.search(query);
            tweets = result.getTweets();
            
        }
        
        return tweets;
    }

    public boolean createConnection() throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(keys.get("ConsumerKey"));
        cb.setOAuthConsumerSecret(keys.get("ConsumerSecret"));
        cb.setOAuthAccessToken(keys.get("AccessToken"));
        cb.setOAuthAccessTokenSecret(keys.get("AccessTokenSecret"));
        
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        System.out.println("Connection established");
        
        /*Query query = new Query("#peace");
        int numberOfTweets = 512;
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();
        while (tweets.size () < numberOfTweets) {
         if (numberOfTweets - tweets.size() > 100)
          query.setCount(100);
         else 
          query.setCount(numberOfTweets - tweets.size());
         try {
          QueryResult result = twitter.search(query);
          tweets.addAll(result.getTweets());
             System.out.println();
          for (Status t: tweets) 
           if(t.getId() < lastID) lastID = t.getId();

        }catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }; 
        query.setMaxId(lastID-1);
        
        }
        
        for (Status tweet : tweets) {
            System.out.println(tweet.getText());
        }*/
        
        return true;
    }
    
    
    
}
