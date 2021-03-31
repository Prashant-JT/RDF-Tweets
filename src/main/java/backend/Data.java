package backend;

import java.util.Date;
import java.util.Locale;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author prash
 */
public class Data {
    private final String term;
    private final long tweetId;
    private final String date;
    private final long replyTo;
    private final long userId;
    private final String userName;
    private final String userLocation;
    private final String languageId;
    private final String languageLabel;
    
    public Data(Status tweet) {
        // User 
        User user = tweet.getUser();
        this.userLocation = user.getLocation();
        this.userName = user.getName();
        this.userId = user.getId();
               
        //Tweet
        this.term = tweet.getText();
        this.date = dateFormat(tweet.getCreatedAt());
        this.tweetId = tweet.getId();
        
        // Retweet
        this.replyTo = tweet.getInReplyToStatusId();
        
        // Language
        this.languageId = tweet.getLang();
        this.languageLabel = new Locale(this.languageId).getDisplayLanguage();
    }
    
    public long getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserLocation() {
        return this.userLocation;
    }
    
    private String dateFormat(Date date) {
        int day = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear() + 1900;
        String res =  day + "/" + month + "/" + year;
        
        return res;
    }
    
    public String getText() {
        return this.term;
    }

    public long getTweetId() {
        return this.tweetId;
    }

    public String getDate() {
        return this.date;
    }

    public long getReplyTo() {
        return this.replyTo;
    }

    public String getLanguageId() {
        return this.languageId;
    }

    public String getLanguageLabel() {
        return this.languageLabel;
    }
    
    public boolean isReply() {
        return replyTo != -1;
    }

}