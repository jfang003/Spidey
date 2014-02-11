/**
 * Created by james on 2/10/14.
 */
public class Record {
    int ID=0;
    public String URL="";
    public String Domain="";
    public String Args="";
    public String Title="";
    public String Body="";
    public String TimeStamp="'1970-01-01 00:00:01'";
    public int UpdateTime=0;
    public String Raw="";
    public int LinksTo=0;
    public int LinksBack=0;
    public int LoadTime=0;
    public String LastUpdate="'1970-01-01 00:00:01'";

    public Record(){

    }

    public String insert(){
        String sql=String.format("Insert into Records (RecordID,`URL`,`Domain`,`Args`,`Title`,`Body`,`TimeStamp`,`UpdateTime`,`Raw`," +
                "`LinksTo`,`LinksBack`,`LoadTime`,`LastUpdate`) Values (NULL,'%s','%s','%s','%s','%s',%s,%d,'%s',%d,%d,%d,%s);",
                URL,Domain,Args,Title,Body,TimeStamp,UpdateTime,Raw,LinksTo,LinksBack,LoadTime,LastUpdate);
        /*sql+=URL+"`,`";
        sql+=Domain+"`,`";
        sql+=Args+"`,`";
        sql+=Title+"`,`";
        sql+=Body+"`,";
        sql+=TimeStamp+",";
        sql+=UpdateTime+",`";
        sql+=Raw+"`,";
        sql+=LinksTo+",";
        sql+=LinksBack+",";
        sql+=LoadTime+",";
        sql+=LastUpdate+");";*/
        return sql;
    }
}
