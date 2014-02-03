/**
 * Created by jason on 2/3/14.
 */
public abstract class Log {

public static void d(String tag, String m, String l) {
    System.out.println(String.format("%s\t%s:\t%s", tag, l, m));
}

    public static String getLevel(int l) {
        String level = "";
        switch (l) {
            case 0:
                level = "Emergency";
                break;
            case 1:
                level = "Alert";
                break;
            case 2:
                level = "Critical";
                break;
            case 3:
                level = "Error";
                break;
            case 4:
                level = "Warning";
                break;
            case 5:
                level = "Notice";
                break;
            case 6:
                level = "Info";
                break;
            case 7:
                level = "Debug";
                break;
        }
        return level;
    }

}
