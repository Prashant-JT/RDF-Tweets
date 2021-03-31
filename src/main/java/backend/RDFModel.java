package backend;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import twitter4j.Status;

/**
 *
 * @author Prashant
 */
public class RDFModel {
    private final String tweetURI = "http://www.si2.com/si2#tweetresource/";
    private final String userURI = "http://www.si2.com/si2#userresource/";
    private final String searchedThemeURI = "http://www.si2.com/si2#searchedthemeresource/";
    private final String themeURI = "http://www.si2.com/si2#themeresource/";
    private final String languageURI = "http://www.si2.com/si2#languageresource/";
    private final String replyURI = "http://www.si2.com/si2#replyresource/"; 
    
    // User input
    private final String theme;
    private final String searchedTerm;
    private final String themeDescription;
    
    private Resource userResource;
    private Resource languageResource;
    private Resource searchedThemeResource;
    private Resource themeResource;
    private Resource tweetResource;
    
    private Property textTweet;
    private Property idTweet;
    private Property dateTweet;
    private Property replyTo;
    
    private Property userName;
    private Property userLocation;
    
    private Property idLanguage;
    private Property languageLabel;
    
    private Property themeProperty;
    
    private Property themeLabelProperty;
    
    public RDFModel(String term, String theme, String themeDescription) {
        //this.model = ModelFactory.createDefaultModel();
        this.searchedTerm = term;
        this.theme = theme;
        this.themeDescription = themeDescription;
    }
    
    public void setModel(Model model) {
        // Set namespaces
        model.setNsPrefix("dc11", "http://purl.org/dc/elements/1.1/");
        model.setNsPrefix("vcard", "http://www.w3.org/2001/vcard-rdf/3.0/");
        model.setNsPrefix("rdfs", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        model.setNsPrefix("rdf", "http://www.w3.org/2000/01/rdf-schema#");
        model.setNsPrefix("voc", this.replyURI);

        replyTo = model.createProperty(replyURI, "reply_to");
        
        userName = model.createProperty(userURI, "nombre");
        userLocation = model.createProperty(userURI, "ubicacion");
        
        idLanguage = model.createProperty(languageURI, "id_idioma");
        languageLabel = model.createProperty(languageURI, "etiqueta");
        
        themeProperty = model.createProperty(searchedThemeURI, "tipo");
        
        themeLabelProperty = model.createProperty(themeURI, "etiqueta");
    }
    
    public Resource createModel(Status status, Model model) {
        return createModel(status, true, model);
    }
    
    public Resource createModel(Status status, boolean checkReply, Model model){
        Data data = new Data(status);
        
        setModel(model);
        createResourceTheme(model);
        createResourceUser(data, model);
        createResourceLanguage(data, model);
        createResourceSearchedTheme(model);
        createResourceTweet(data, model);
        
        if (checkReply && data.isReply()){
            tweetResource.addProperty(replyTo, 
                    createModel(TwitterConnection.getStatus(data.getReplyTo()), false, model));
        }
        
        return tweetResource;
    }
    
    private void createResourceTweet(Data data, Model model) {
        // Resource 'tweet'
        this.tweetResource = model.createResource(tweetURI + data.getTweetId());
        
        // Add properties
        tweetResource.addProperty(DC_11.description, data.getText());
        tweetResource.addProperty(DC_11.identifier, (String.valueOf(data.getTweetId())));
        tweetResource.addProperty(DC_11.date, data.getDate());
        tweetResource.addProperty(DC_11.creator, this.userResource);
        tweetResource.addProperty(DC_11.language, this.languageResource);
        tweetResource.addProperty(DC_11.relation, this.searchedThemeResource);
    }

    private void createResourceUser(Data data, Model model) {
        // Resource 'user'
        this.userResource = model.createResource(userURI + data.getUserId());

        // Add properties
        userResource.addProperty(DC_11.publisher, data.getUserName());
        userResource.addProperty(DC_11.coverage, data.getUserLocation());
    }

    private void createResourceLanguage(Data data, Model model) {
        // Resource 'language'
        this.languageResource = model.createResource(languageURI + data.getLanguageId());

        // Add properties
        languageResource.addProperty(DC_11.identifier, data.getLanguageId());
        languageResource.addProperty(DC_11.description, data.getLanguageLabel());
    }

    private void createResourceSearchedTheme(Model model) {
        // Resource 'searchedTheme'
        this.searchedThemeResource = model.createResource(searchedThemeURI + this.searchedTerm);
        
        // Add properties
        this.searchedThemeResource.addProperty(DC_11.type, this.themeResource);
    }

    private void createResourceTheme(Model model) {
        // Resource 'theme'
        this.themeResource = model.createResource(themeURI + this.theme);
        
        // Add properties
        this.themeResource.addProperty(RDF.type, RDFS.Class);
        this.themeResource.addProperty(RDFS.label, this.themeDescription);
    }
    
}