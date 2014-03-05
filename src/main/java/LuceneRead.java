import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import java.io.IOException;
import org.apache.lucene.store.FSDirectory;
import java.io.File;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.util.Version;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

/**
 * Created by james on 2/10/14.
 */

public class LuceneRead {
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    public static void main(String[] arg) throws IOException, ParseException {
        // the text you want to search
        if(arg.length!=3) {
            System.out.println("Expected <directory> <index_field> <query>");
            System.exit(0);
        }
        String querytext = arg[2];
        File indexDirectory = new File(arg[0]);
        IndexReader reader = IndexReader.open(FSDirectory.open(indexDirectory));
        System.out.println(reader.numDocs());
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        QueryParser parser = new QueryParser(Version.LUCENE_40, arg[1], analyzer);
        parser.setAllowLeadingWildcard(true);
        Query q = parser.parse(querytext);
        int hitsPerPage = 50;
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println("Found a record url:" + d.get("URL"));
        }
    }

}


