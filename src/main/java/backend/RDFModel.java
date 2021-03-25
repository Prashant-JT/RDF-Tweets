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
        Property textTweet = this.model.createProperty(tweetURI, "tweet");
        Property idTweet = this.model.createProperty(tweetURI, "id_tweet");
        Property dateTweet = this.model.createProperty(tweetURI, "date_tweet");
        
        // Add properties
        tweetResource.addProperty(DC_11.description, textTweet);
        tweetResource.addProperty(DC_11.identifier, idTweet);
        tweetResource.addProperty(DC_11.date, dateTweet);
    }

    private void createResourceUser() {
        // Resource 'user'
        Resource userResource = this.model.createResource(userURI);
        // Properties of 'user'
        Property userName = this.model.createProperty(userURI, "user_name");
        Property userLocation = this.model.createProperty(userURI, "user_location");
        
        // Add properties
        userResource.addProperty(DC_11.publisher, userName);
        userResource.addProperty(DC_11.coverage, userLocation);
    }

    private void createResourceLanguage() {
        // Resource 'language'
        Resource languageResource = this.model.createResource(languageURI);
        // Properties of 'theme'
        Property languageLabel = this.model.createProperty(languageURI, "language_label");
        Property idLanguage = this.model.createProperty(languageURI, "id_language");
        
        // Add properties
        languageResource.addProperty(DC_11.description, languageLabel);
        languageResource.addProperty(DC_11.identifier, idLanguage);
    }

    private void createResourceSearchedTheme() {
        // Resource 'searchedTheme'
        Resource searchedThemeResource = this.model.createResource(searchedThemeURI);
    }

    private void createResourceTheme() {
        // Resource 'theme'
        Resource themeResource = this.model.createResource(themeURI);
        // Properties of 'theme'
        Property themeLabel = this.model.createProperty(themeURI, "theme_label");
        
        // Add properties
        themeResource.addProperty(DC_11.description, themeLabel);
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