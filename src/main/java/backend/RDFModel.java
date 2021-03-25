package backend;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.DC_11;

/**
 *
 * @author Prashant
 */
public class RDFModel {
    private final Model model;
    private final String tweetURI = "http://www.si2.com/tweetresource/";
    private final String userURI = "http://www.si2.com/userresource/";
    private final String searchedThemeURI = "http://www.si2.com/searchedthemeresource/";
    private final String themeURI = "http://www.si2.com/themeresource/";
    private final String languageURI = "http://www.si2.com/languageresource/";
    private final String replyURI = "http://www.si2.com/replyresource/";
        
    public RDFModel() {
        this.model = ModelFactory.createDefaultModel();
        this.createModel();
    }

    private void createModel() {
        this.createResourceTweet();
        this.createResourceUser();
        this.createResourceLanguage();
        this.createResourceSearchedTheme();
        this.createResourceTheme();
        this.createResourceReplyTo();
    }

    private void createResourceTweet() {
        // Resource 'tweet'
        Resource tweetResource = this.model.createResource(tweetURI);
        
        // Properties of 'tweet'
        Property textTweet = model.createProperty(tweetURI, "tweet");
        Property idTweet = model.createProperty(tweetURI, "id_tweet");
        Property dateTweet = model.createProperty(tweetURI, "date_tweet");
        Property languageTweet = model.createProperty(tweetURI, "language_tweet");
        Property searchTweet = model.createProperty(tweetURI, "search_tweet");
        Property authorTweet = model.createProperty(tweetURI, "author_tweet");
        Property replyTweet = model.createProperty(tweetURI, "reply_tweet");
        
        // Add properties
        tweetResource.addProperty(DC_11.description, textTweet);
        tweetResource.addProperty(DC_11.identifier, idTweet);
        tweetResource.addProperty(DC_11.date, dateTweet);
    }

    private void createResourceUser() {
        // Resource 'user'
        Resource userResource = this.model.createResource(userURI);
        // Properties of 'user'
        Property userName = model.createProperty(userURI, "user_name");
        Property userLocation = model.createProperty(userURI, "user_location");
    }

    private void createResourceLanguage() {
        // Resource 'language'
        Resource languageResource = this.model.createResource(languageURI);
        // Properties of 'theme'
        Property languageLabel = model.createProperty(languageURI, "language_label");
        Property idLanguage = model.createProperty(languageURI, "id_language");
    }

    private void createResourceSearchedTheme() {
        // Resource 'searchedTheme'
        Resource searchedThemeResource = this.model.createResource(searchedThemeURI);
        // Properties of 'searchedTheme'
        Property type = model.createProperty(searchedThemeURI, "theme_type");
    }

    private void createResourceTheme() {
        // Resource 'theme'
        Resource themeResource = this.model.createResource(themeURI);
        // Properties of 'theme'
        Property themeLabel = model.createProperty(themeURI, "theme_label");
        //themeResource.addProperty(vocabulario, themeLabel);
    }

    private void createResourceReplyTo() {
        // Resource 'replyTweet'
        Resource replyResource = this.model.createResource(replyURI);
        // ???
    }

    
    public void printGraph() {
        System.out.println("++++++++++++++++++++++");
        RDFDataMgr.write(System.out, this.model, RDFFormat.TURTLE_PRETTY);
        System.out.println("++++++++++++++++++++++");
    }
    
}