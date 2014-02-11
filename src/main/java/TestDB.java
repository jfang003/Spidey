/**
 * Created by james on 2/10/14.
 */
public class TestDB {
    int ID;
    public String URL;
    public int links;
    public String time;

    public TestDB(){
        URL="Blah";
        links=5;
        time="1970-01-01 00:00:01";
    }

    public String insert(){
        String sql="Insert into Spidey.TestDB(ID,URL,Links,Time) VALUES (NULL,'"+URL+"',"+links+",'"+time+"';";
        return sql;
    }
}
