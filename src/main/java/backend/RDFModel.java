package backend;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.VCARD;

/**
 *
 * @author Prashant
 */
public class RDFModel {
    private final Model model;
    private final String tweetURI = "http://www.si2.com/si2#tweetresource";
    private final String userURI = "http://www.si2.com/si2#userresource";
    private final String searchedThemeURI = "http://www.si2.com/si2#searchedthemeresource";
    private final String themeURI = "http://www.si2.com/si2#themeresource";
    private final String languageURI = "http://www.si2.com/si2#languageresource";
    private final String replyURI = "http://www.si2.com/si2#replyresource";
    
    private Resource userResource;
    private Resource languageResource;
    private Resource searchedThemeResource;
    private Resource themeResource;
    private Resource tweetResource;
    private Resource replyResource;
    
    public RDFModel() {
        this.model = ModelFactory.createDefaultModel();
        this.model.setNsPrefix("si2", "http://www.si2.com/si2#");
        this.model.setNsPrefix("dc11", "http://purl.org/dc/elements/1.1/");
        this.model.setNsPrefix("vcard", "http://www.w3.org/2001/vcard-rdf/3.0#");
        this.createModel();
    }

    private void createModel() {
        this.createResourceUser();
        this.createResourceLanguage();
        this.createResourceTheme();
        this.createResourceSearchedTheme();
        this.createResourceTweet();
        this.createResourceReplyTo();
    }

    private void createResourceTweet() {
        // Resource 'tweet'
        this.tweetResource = this.model.createResource(tweetURI);
        
        // Properties of 'tweet'
        Property textTweet = this.model.createProperty(tweetURI, "tweet");
        Property idTweet = this.model.createProperty(tweetURI, "id_tweet");
        Property dateTweet = this.model.createProperty(tweetURI, "date_tweet");
        
        // Add properties
        tweetResource.addProperty(DC_11.description, textTweet);
        tweetResource.addProperty(DC_11.identifier, idTweet);
        tweetResource.addProperty(DC_11.date, dateTweet);
        tweetResource.addProperty(DC_11.creator, this.userResource);
        tweetResource.addProperty(DC_11.language, this.languageResource);
        tweetResource.addProperty(DC_11.relation, this.searchedThemeResource);
    }

    private void createResourceUser() {
        // Resource 'user'
        this.userResource = this.model.createResource(userURI);
        // Properties of 'user'
        Property userName = this.model.createProperty(userURI, "user_name");
        Property userLocation = this.model.createProperty(userURI, "user_location");
        
        // Add properties
        userResource.addProperty(DC_11.publisher, userName);
        userResource.addProperty(DC_11.coverage, userLocation);
    }

    private void createResourceLanguage() {
        // Resource 'language'
        this.languageResource = this.model.createResource(languageURI);
        // Properties of 'theme'
        Property languageLabel = this.model.createProperty(languageURI, "language_label");
        Property idLanguage = this.model.createProperty(languageURI, "id_language");
        
        // Add properties
        languageResource.addProperty(DC_11.description, languageLabel);
        languageResource.addProperty(DC_11.identifier, idLanguage);
    }

    private void createResourceSearchedTheme() {
        // Resource 'searchedTheme'
        this.searchedThemeResource = this.model.createResource(searchedThemeURI);
        
        // Add properties
        this.searchedThemeResource.addProperty(DC_11.type, this.themeResource);
    }

    private void createResourceTheme() {
        // Resource 'theme'
        this.themeResource = this.model.createResource(themeURI);
        // Properties of 'theme'
        Property themeLabel = this.model.createProperty(themeURI, "theme_label");
        
        // Add properties
        this.themeResource.addProperty(DC_11.description, themeLabel);
    }

    private void createResourceReplyTo() {
        // Resource 'replyTweet'
        this.replyResource = this.model.createResource(replyURI);
         
        this.tweetResource.addProperty(VCARD.SOURCE, this.replyResource);
    }

    
    public void saveGraph() {
        System.out.println("++++++++++++++++++++++");
        RDFDataMgr.write(System.out, this.model, RDFFormat.TURTLE_BLOCKS);
        System.out.println("++++++++++++++++++++++");
    }
    
}