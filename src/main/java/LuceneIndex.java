/**
 * Created by james on 2/9/14.
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.IntField;

public class LuceneIndex {
    public static void main(String[] args) throws Exception {
        //create a file which will hold the index files.make sur the path is correct
        boolean URL=true;
        boolean Body=true;
        boolean Domain=false;
        boolean Args=false;
        boolean Title=false;
        boolean TimeStamp=false;
        boolean UpdateTime=false;
        boolean Raw=false;
        boolean LinksTo=false;
        boolean LinkBacks=false;
        boolean LoadTime=false;
        boolean Head=false;
        for (int i=0;i<args.length;i++)
        {
            System.out.println(args[i]);
            if(args[i].equals("URL")) URL=true;
            else if(args[i].equals("Domain")) Domain=true;
            else if(args[i].equals("Args")) Args=true;
            else if(args[i].equals("Title")) Title=true;
            else if(args[i].equals("Body")) Body=true;
            else if(args[i].equals("TimeStamp")) TimeStamp=true;
            else if(args[i].equals("UpdateTime")) UpdateTime=true;
            else if(args[i].equals("Raw")) Raw=true;
            else if(args[i].equals("LinksTo")) LinksTo=true;
            else if(args[i].equals("LinkBacks")) LinkBacks=true;
            else if(args[i].equals("Head")) Head=true;
            else
            {
                System.out.println("Incorrect Parameter");
                System.exit(0);
            }
        }
        File file = new File("/home/james/lucene_index");
        //establish the mysql connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Spidey", "root", "root");
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_40,analyzer);
        Directory indexDir = FSDirectory.open(file);
        IndexWriter writer = new IndexWriter(indexDir,conf);
        String query = "select * from records";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        //according to the results create the index files
        while (result.next()) {
            System.out.println("Retrieved ID:["+result.getString("ID")+"]");
            Document document = new Document();
            //add the fields to the index as you required
            document.add(new IntField("id", result.getInt("ID"), Field.Store.NO));
            if(Body) {
                try{
                document.add(new TextField("body", result.getString("Body"), Field.Store.YES));
                System.out.println("Body");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Body",4);}
            }
            if(URL){
                try{
                document.add(new TextField("URL", result.getString("URL"), Field.Store.YES));
                System.out.println("URL");
                }
                catch(Exception e){Log.d("Lucene Index","Empty URL",4);}
            }
            if(Args){
                try{
                document.add(new TextField("Args", result.getString("Args"), Field.Store.YES));
                System.out.println("Args");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Args",4);}
            }
            if(Domain){
                try{
                document.add(new TextField("Domain", result.getString("Domain"), Field.Store.YES));
                System.out.println("Domain");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Domain",4);}
            }
            if(Title){
                try{
                document.add(new TextField("Title", result.getString("Title"), Field.Store.YES));
                System.out.println("Title");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Title",4);}
            }
            if(TimeStamp){
                try{
                document.add(new TextField("TimeStamp", result.getString("TimeStamp"), Field.Store.YES));
                System.out.println("TimeStamp");
                }
                catch(Exception e){Log.d("Lucene Index","Empty TimeStamp",4);}
            }
            if(Raw){
                try{
                document.add(new TextField("Raw", result.getString("Raw"), Field.Store.YES));
                System.out.println("Raw");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Raw",4);}
            }
            if(Head){
                try{
                document.add(new TextField("Head", result.getString("Head"), Field.Store.YES));
                System.out.println("Head");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Head",4);}
            }
            if(LoadTime)
            {
                try{
                document.add(new IntField("LoadTime", result.getInt("LoadTime"), Field.Store.NO));
                System.out.println("Load Time");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Load Time",4);}
            }
            if(UpdateTime)
            {
                try{
                document.add(new TextField("UpdateTime", result.getString("UpdateTime"), Field.Store.NO));
                System.out.println("Update Time");
                }
                catch(Exception e){Log.d("Lucene Index","Empty Update Time",4);}
            }
            if(LinksTo)
            {
                try{
                document.add(new IntField("LinksTo", result.getInt("LinksTo"), Field.Store.NO));
                System.out.println("LinksTo");
                }
                catch(Exception e){Log.d("Lucene Index","Null LinksTo",4);}
            }
            if(LinkBacks)
            {
                try{
                document.add(new IntField("LinkBacks", result.getInt("LinkBacks"), Field.Store.NO));
                System.out.println("LinkBacks");
                }
                catch(Exception e){Log.d("Lucene Index","Null LinkBacks",4);}
            }
            //document.add(new Field("", result.getString("COMP_DESC"), Field.Store.YES, Field.Index.ANALYZED));
            //create the index files
            writer.updateDocument(new Term("id", result.getString("ID")), document);
        }
        writer.close();
    }
}
