package backend;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

/**
 *
 * @author prash
 */
public class FileReader {
    private final String fileName;
    private final Map<String, String> keys;
    
    public FileReader(String fileName) {
        this.fileName = fileName;
        this.keys = new HashMap<>();
    }
    
    public void read() {
        File file = new File(fileName);
        Scanner myReader = null;
        try {
            myReader = new Scanner(file);
        } catch (Exception ex) {
            System.out.println("Error reading file");
        }
        
        List<String> keys = new ArrayList<>();
        while (myReader.hasNextLine()) {
            keys.add(myReader.nextLine());
        }
        this.saveKeys(keys);
        
        myReader.close();
    }

    private void saveKeys(List<String> keys) {
        if (keys.size() == 4) {
            this.keys.put("ConsumerKey", keys.get(0));
            this.keys.put("ConsumerSecret", keys.get(1));
            this.keys.put("AccessToken", keys.get(2));
            this.keys.put("AccessTokenSecret", keys.get(3));
        }else{
            System.out.println("Error in file");
        }
    }
    
    public Map<String, String> getKeys() {
        return this.keys;
    }
    
}
 
