import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
/**
 * Created by jason on 1/21/14.
 */
public class Spidey {

    public static void main(String[] args) throws SQLException {
        // Number of crawlers/threads to create
        int totalCrawlers;
        // Arraylist holding our threads
        ArrayList<Thread> threads = new ArrayList<Thread>();
        // Arraylist holding our crawler
        ArrayList<Crawler> crawlers = new ArrayList<Crawler>();
        // temp path, not implemented
        String filePath = "/Volumes/Virtual Machines/spidey/";
        // just a list of sites to start for our seed
        String[] eduSites = {"http://www.ucr.edu", "http://www.mit.edu", "http://www.siu.edu",
                "http://www.niu.edu", "http://www.harvard.edu"};
        boolean isDone = false;
        String TAG = "Spidey";
        DB db = new DB();
        String sql = "Select * From Records";
        ResultSet rs;
        rs = db.runSql(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        System.out.println("querying SELECT * FROM Records");
        System.out.println(rsmd.getColumnCount());
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
        Record r = new Record();
        sql = r.insert();
        System.out.println(sql);

        try {
            db.runSql2(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(args.length > 0) {
        //if(!args[0].isEmpty()) {
            totalCrawlers = Integer.parseInt(args[0]);
            if(totalCrawlers > 5) {
                totalCrawlers = 5;
            }
        } else {
            totalCrawlers = 2;
        }

        // This primes our seed for each crawler
        for(int i=0; i<totalCrawlers;i++) {
            // This allows us to save our crawler in our arraylist
            Crawler c = new Crawler(filePath, eduSites[i]);
            crawlers.add(c);
            // let it be threadable
            Thread worker = new Thread(c);
            // add our thread to our arraylist
            threads.add(worker);
        }

        // Using this we can start to crawl sites
        for(Thread t : threads) {
            t.start();
        }

        // Kill threads when done
        int pos = 0;
        while(!isDone) {
            for(Crawler c : crawlers) {
                if(c.getQueueSize() < 1) {
                    pos = crawlers.indexOf(c);
                    threads.get(pos).interrupt();
                    threads.remove(pos);
                }
            }
            if(threads.size() < 1) {
                isDone = true;
                Log.d(TAG, "We are done crawling!", 7);
            }
        }
        /**
         // easy way to cycle through them all
         for(Crawler c : crawlers) {
         }
         **/

        System.out.println("There are " + crawlers.size() + " crawlers ready to sling!");
    }
}
