package backend;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;
import twitter4j.Status;

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
    // User input
    private final String theme;
    private final String searchedTerm;
    
    private Resource userResource;
    private Resource languageResource;
    private Resource searchedThemeResource;
    private Resource themeResource;
    private Resource tweetResource;
    private Resource replyResource;
    
    // Properties of 'tweet'
    private Property textTweet;
    private Property idTweet;
    private Property dateTweet;
    private Property replyTo;
    
    // Properties of 'user'
    private Property userName;
    private Property userLocation;
    
    // Properties of 'language'
    private Property idLanguage;
    private Property languageLabel;
    
    // Properties of 'term'
    Property themeProperty;
    
    // Properties of 'theme'
    Property themeLabelProperty;
    
    public RDFModel(String term, String theme) {
        this.model = ModelFactory.createDefaultModel();
        this.searchedTerm = term;
        this.theme = theme;
        
        // Set namespaces
        this.model.setNsPrefix("si2", "http://www.si2.com/si2#");
        this.model.setNsPrefix("dc11", "http://purl.org/dc/elements/1.1/");
        this.model.setNsPrefix("vcard", "http://www.w3.org/2001/vcard-rdf/3.0#");
        
        textTweet = model.createProperty(tweetURI, "texto");
        idTweet = model.createProperty(tweetURI, "id_tweet");
        dateTweet = model.createProperty(tweetURI, "fecha_creacion");
        replyTo = model.createProperty(tweetURI, "respuesta_a");
        userResource = model.createProperty(tweetURI, "autor");
        languageResource = model.createProperty(tweetURI, "idioma");
        searchedThemeResource = model.createProperty(tweetURI, "relacionado_con");
        
        userName = model.createProperty(userURI, "nombre");
        userLocation = model.createProperty(userURI, "ubicacion");
        
        idLanguage = model.createProperty(languageURI, "id_idioma");
        languageLabel = model.createProperty(languageURI, "etiqueta");
        
        themeProperty = model.createProperty(searchedThemeURI, "tipo");
        
        themeLabelProperty = model.createProperty(themeURI, "etiqueta");
    }
    
    public Resource createModel(Status status){
        return createModel(status, true);
    }
    
    public Resource createModel(Status status, boolean checkReply){
        Data data = new Data(status);
        
        createResourceTweet(data);
        createResourceUser(data);
        createResourceLanguage(data);
        createResourceSearchedTheme();
        createResourceTheme();
        
        searchedThemeResource.addProperty(RDF.type, themeResource);
        //tweetResource.addProperty(this.searchedTerm, searchedThemeResource);
        //tweetResource.addProperty(this.userProperty, userResource);
        //tweetResource.addProperty(this.languageProperty, languageResource);
        
        if (checkReply && data.isReply()){
            tweetResource.addProperty(replyTo, createModel(TwitterConnection.getStatus(data.getReplyTo()), false));
        }
        
        return tweetResource;
    }
    
    private void createResourceTweet(Data data) {
        // Resource 'tweet'
        this.tweetResource = this.model.createResource(tweetURI + data.getTweetId());
        
        // Properties of 'tweet'
        textTweet = this.model.createProperty(tweetURI, data.getText());
        idTweet = this.model.createProperty(tweetURI, data.getTweetId() + "");
        dateTweet = this.model.createProperty(tweetURI, data.getDate());
        
        // Add properties
        tweetResource.addProperty(DC_11.description, textTweet);
        tweetResource.addProperty(DC_11.identifier, idTweet);
        tweetResource.addProperty(DC_11.date, dateTweet);
        tweetResource.addProperty(DC_11.creator, this.userResource);
        tweetResource.addProperty(DC_11.language, this.languageResource);
        tweetResource.addProperty(DC_11.relation, this.searchedThemeResource);
    }

    private void createResourceUser(Data data) {
        // Resource 'user'
        this.userResource = this.model.createResource(userURI + data.getUserId());
        // Properties of 'user'
        userName = this.model.createProperty(userURI, data.getUserName());
        userLocation = this.model.createProperty(userURI, data.getUserLocation());
        
        // Add properties
        userResource.addProperty(DC_11.publisher, userName);
        userResource.addProperty(DC_11.coverage, userLocation);
    }

    private void createResourceLanguage(Data data) {
        // Resource 'language'
        this.languageResource = this.model.createResource(languageURI + data.getLanguageId());
        // Properties of 'theme'
        idLanguage = this.model.createProperty(languageURI, data.getLanguageId());
        languageLabel = this.model.createProperty(languageURI, data.getLanguageLabel());
        
        // Add properties
        languageResource.addProperty(DC_11.identifier, idLanguage);
        languageResource.addProperty(DC_11.description, languageLabel);
    }

    private void createResourceSearchedTheme() {
        // Resource 'searchedTheme'
        this.searchedThemeResource = this.model.createResource(searchedThemeURI + this.searchedTerm);
        
        // Add properties
        //this.searchedThemeResource.addProperty(DC_11.type, this.themeResource);
    }

    private void createResourceTheme() {
        // Resource 'theme'
        this.themeResource = this.model.createResource(themeURI + this.theme);

        // Add properties
        this.themeResource.addProperty(RDF.type, RDFS.Class);
        this.themeResource.addProperty(RDFS.label, this.theme);
    }

    private void createResourceReplyTo() {
        // Resource 'replyTweet'
        this.replyResource = this.model.createResource(replyURI);
         
        this.tweetResource.addProperty(VCARD.SOURCE, this.replyResource);
    }
    
    public Model getModel() {
        return this.model;
    }
    
}