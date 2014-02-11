/**
 * Created by james on 2/9/14.
 * http://kalanir.blogspot.com/2008/06/indexing-database-using-apache-lucene.html as the guideline
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.store.Directory;

public class LuceneIndex {
    public static void main(String[] args) throws Exception {
        //create a file which will hold the index files.make sur the path is correct
        File file = new File("/home/james/lucene_index");
        //establish the mysql connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Spidey", "root", "mastery1");
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_40,analyzer);
        Directory indexDir = FSDirectory.open(file);
        IndexWriter writer = new IndexWriter(indexDir,conf);
        String query = "select * from Records";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        //according to the results create the index files
        while (result.next()) {
            System.out.println("Retrieved ID:["+result.getString("RecordID")+"]");
            Document document = new Document();
            //add the fields to the index as you required
            document.add(new Field("id", result.getString("RecordID"), Field.Store.YES, Field.Index.NOT_ANALYZED));
            document.add(new Field("body", result.getString("Body"), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("URL", result.getString("URL"), Field.Store.YES, Field.Index.ANALYZED));
            //document.add(new Field("", result.getString("COMP_DESC"), Field.Store.YES, Field.Index.ANALYZED));
            //create the index files
            writer.updateDocument(new Term("id", result.getString("RecordID")), document);
        }
        writer.close();
    }
}
