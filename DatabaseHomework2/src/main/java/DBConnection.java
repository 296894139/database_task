import java.sql.DriverManager;

public class DBConnection {
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "root";
    private static final String password = "19971029";
    private static final String URL = "jdbc:mysql://localhost:3306/work2"+"?serverTimezone=GMT%2B8";

    public DBConnection() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public java.sql.Connection getConnection() {
        java.sql.Connection con = null;
        try {
            con = DriverManager.getConnection(URL, userName, password);

            if (con == null) {
                System.out.println("connection is null!");
            } else {
                System.out.println("connect to =>" + con);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return con;
    }

}
