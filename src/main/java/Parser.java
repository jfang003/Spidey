import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jason on 2/3/14.
 */
public class Parser extends Crawler {

    // Our connection to websites
    protected Document mDocument;
    // Domain portion of the url
    String mDomain;
    // Troubleshooting tag for logging
    String TAG = "parser";
    // URL validator
    UrlValidator mURLValidator;

    public Parser() {
        this.mURLValidator = new UrlValidator(new String[]{"http", "https"});
    }

    public void parseDocument(String url) {
        try{
            this.mDocument = Jsoup.connect(url).get(); // get the next page
        } catch(IOException e) {
            Log.d(TAG,String.format("Error connecting to %s: %s", url, e.toString()), 4);
        }
    }

    public ArrayList<String> getUrls() {
        System.out.println("Getting urls");
        ArrayList<String> links = new ArrayList<String>();
        // Get all the links on a page
        // Start building our seed
        for(Element e : this.mDocument.getElementsByTag("a")) {
            // make sure the url is valid, if it is push it onto our "queue"
            String url = e.attr("href");
            // make sure this is a valid URL
            if(this.mURLValidator.isValid(url)) {
                Log.d(TAG, String.format("This url: %s is valid", url), 6);
                // Add the link to our arraylist, if it doesn't contain it
                if(!super.inQueue(url)) {
                    links.add(url);
                } else {
                    Log.d(TAG, String.format("We already have: %s in our queue", e.attr("href")), 6);
                }
            } else {
                Log.d(TAG, String.format("This url: %s is not a valid url", url), 6);
            }
        }
        Log.d(TAG, String.format("Size of links: %s", links.size()), 6);
        return links;
    }


}
