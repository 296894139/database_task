import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @创建人 徐介晖
 * @创建时间 2018/10/23
 * @描述
 */
public class Util {
    public Util() {
    }
    /*
     执行数据库语句
     */
    public void executeSQLNoResult(String s,Connection con){
        try (Statement statement = con.createStatement()) {
            statement.execute(s);
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
    public ResultSet executeSQL(String s, Connection con) {
        try (Statement statement = con.createStatement()) {

           // System.out.println(s);
          ResultSet re=statement.executeQuery(s);
         // re.next();
        //  System.out.println("tem:"+re.getDouble(1));
         /* while(re!=null){
              System.out.println(re.getString(2));
              re.next();
          }*/
          //  con.commit();
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
