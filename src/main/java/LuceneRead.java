import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import java.io.IOException;
import org.apache.lucene.store.FSDirectory;
import java.io.File;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;


/**
 * Created by james on 2/10/14.
 */
public class LuceneRead {
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    public void main(String[] arg) throws IOException {
        // the text you want to search
        String querytext = "kandy";
        File indexDirectory = new File("YourIndexLocation");
        IndexReader reader = IndexReader.open(FSDirectory.open(indexDirectory));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("name", analyzer);
        Query query = null;
        try {
            query = parser.parse(querytext);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Hits hits = searcher.search(query);
        for(int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            String hotelName = doc.get("name");
        }
    }

}


