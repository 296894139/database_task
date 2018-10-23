
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Util {
    public Util() {
    }
    /*
     执行数据库语句
     */
    public ResultSet executeSQL(String s, Connection con) {
        try (Statement statement = con.createStatement()) {
         // statement.execute(s);
            System.out.println(s);
          ResultSet re=statement.executeQuery(s);
          re.next();
         /* while(re!=null){
              System.out.println(re.getString(2));
              re.next();
          }*/
            con.commit();
            return  re;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /*
      执行数据库语句
     */
    public void executeSQLs(String[] statements, Connection con) {
        try (Statement statement = con.createStatement()) {
            for (String s : statements) {
                statement.addBatch(s);
            }
            statement.executeBatch();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
