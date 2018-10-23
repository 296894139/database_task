/**
 * @创建人 徐介晖
 * @创建时间 2018/10/23
 * @描述
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Assignment1 {
    Connection connection;
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/work2"+"?serverTimezone=GMT%2B8";
    private final static String USER = "root";
    private final static String PW = "19971029";

    private static final String CREATE_CUSTOMER = "CREATE TABLE Customers(cid varchar(255) PRIMARY KEY not null," +
            "cname varchar(255) not null," +
            "city varchar(255) not null," +
            "discnt double(16, 2))charset=utf8;";

    private static final String CREATE_AGENTS = "CREATE TABLE Agents(aid varchar(255) PRIMARY KEY not null," +
            "aname varchar(255) not null," +
            "city varchar(255) not null," +
            "percent int(10))charset=utf8;";

    private static final String CREATE_PRODUCTS = "CREATE TABLE products(pid varchar(255) PRIMARY KEY not null," +
            "pname varchar(255)," +
            "city varchar(255) not null," +
            "quantity int(10),"+
            "price double(10, 2))charset=utf8;";

    private static final String CREATE_ORDERS = "CREATE TABLE Orders(oid int(10) PRIMARY KEY not null," +
            "month varchar(255) not null,"+
            "cid varchar(255) not null," +
            "aid varchar(255) not null," +
            "pid varchar(255) not null," +
            "qty int(10)," +
            "dollars double(10, 2))charset=utf8;";


    private void init(){
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PW);
            if(!connection.isClosed()) {
                System.out.println("Succeed to connect...");


            }else{
                System.out.println("Fail to connect...");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void createAll(){
        try {
            Statement statement = connection.createStatement();
            if(statement.executeLargeUpdate(CREATE_CUSTOMER) == 0 && statement.executeLargeUpdate(CREATE_AGENTS) == 0
                    && statement.executeLargeUpdate(CREATE_ORDERS) == 0 &&
                    statement.executeLargeUpdate(CREATE_PRODUCTS) == 0){
                System.out.println("Succeed to create all...");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void insert(){
        try {
            Statement statement = connection.createStatement();
            //insert Customers
            statement.executeUpdate("INSERT INTO Customers VALUES ('c001', 'Tiptop', 'Duluth', 10.00)");
            statement.executeUpdate("INSERT INTO Customers VALUES ('c002', 'Basics', 'Dallas', 12.00)");
            statement.executeUpdate("INSERT INTO Customers VALUES ('c003', 'Allied', 'Dallas', 8.00)");
            statement.executeUpdate("INSERT INTO Customers VALUES ('c004', 'ACME',   'Duluth', 8.00)");
            statement.executeUpdate("INSERT INTO Customers VALUES ('c006', 'ACME',   'Kyoto',  0.00)");

            //insert Agents
            statement.executeUpdate("INSERT INTO Agents VALUES ('a01', 'Smith', 'New York', 6)");
            statement.executeUpdate("INSERT INTO Agents VALUES ('a02', 'Jones', 'Newark', 6)");
            statement.executeUpdate("INSERT INTO Agents VALUES ('a03', 'Brown', 'Tokyo', 7)");
            statement.executeUpdate("INSERT INTO Agents VALUES ('a04', 'Gray',  'New York', 6)");
            statement.executeUpdate("INSERT INTO Agents VALUES ('a05', 'Otasi', 'Duluth', 5)");
            statement.executeUpdate("INSERT INTO Agents VALUES ('a06', 'Smith', 'Dallas', 5)");

            //insert Products
            statement.executeUpdate("INSERT INTO products VALUES ('p01', 'comb', 'Dallas', 111400, 0.50)");
            statement.executeUpdate("INSERT INTO products VALUES ('p02', 'brush',  'Newark', 203000, 0.50)");
            statement.executeUpdate("INSERT INTO products VALUES ('p03', 'razor', 'Duluth', 150600, 1.00)");
            statement.executeUpdate("INSERT INTO products VALUES ('p04', 'pen', 'Duluth', 125300, 1.00)");
            statement.executeUpdate("INSERT INTO products VALUES ('p05', 'pencil', 'Dallas', 221400, 1.00)");
            statement.executeUpdate("INSERT INTO products VALUES ('p06', 'folder', 'Dallas', 123100, 2.00)");
            statement.executeUpdate("INSERT INTO products VALUES ('p07', 'case',   'Newark', 100500, 1.00)");

            //insert Orders
            statement.executeUpdate("INSERT INTO Orders VALUES (1011, 'jan', 'c001', 'a01', 'p01', 1000, 450.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1012, 'jan', 'c001', 'a01', 'p01', 1000, 450.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1019, 'feb', 'c001', 'a02', 'p02', 400,  180.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1017, 'feb', 'c001', 'a06', 'p03', 600,  540.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1018, 'feb', 'c001', 'a03', 'p04', 600,  540.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1023, 'mar', 'c001', 'a04', 'p05', 500,  450.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1022, 'mar', 'c001', 'a05', 'p06', 400,  720.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1025, 'apr', 'c001', 'a05', 'p07', 800,  720.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1013, 'jan', 'c002', 'a03', 'p03', 1000, 880.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1026, 'may', 'c002', 'a05', 'p03', 800,  704.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1015, 'jan', 'c003', 'a03', 'p05', 1200, 1104.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1014, 'jan', 'c003', 'a03', 'p05', 1200, 1104.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1021, 'feb', 'c004', 'a06', 'p01', 1000, 460.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1016, 'jan', 'c006', 'a01', 'p01', 1000, 500.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1020, 'feb', 'c006', 'a03', 'p07', 600,  600.00)");
            statement.executeUpdate("INSERT INTO Orders VALUES (1024, 'mar', 'c006', 'a06', 'p01', 800,  400.00)");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void solution1(){
        try{
            System.out.println("每个经销商销售每一种产品的总数量:");
            ResultSet res = execute("SELECT pid, aid, sum(qty) FROM Orders o GROUP BY pid, aid");
            assert res != null;
            while (res.next()){
                System.out.println("pid: " +res.getString(1) + " aid: " +res.getString(2) +" "+
                        "count: "+ res.getString(3));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution2(){
        try {
            System.out.println("在该客户订购过的所有商品中，每一种商品的平均每笔订单的订购数量均达到或超过300的客户的编号:");
            ResultSet resultSet = execute("SELECT t.cid FROM (SELECT cid, pid, AVG(qty) as " +
                    "total_avg FROM Orders GROUP BY cid, pid)t  GROUP BY t.cid HAVING min(t.total_avg) >= 300");
            printOneColumn(resultSet, "cid");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 居然为空...
     */

    private void solution3(){
        try {
            System.out.println("没有为居住在Duluth的任何客户订购过任何商品的经销商的编号:");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT aid FROM Agents WHERE " +
                    "aid NOT IN (SELECT aid FROM Customers C, Orders O WHERE C.cid=O.cid AND " +
                    "C.city='Duluth')");
            if(!resultSet.next())
                System.out.println("查询结果为空...");
            else{
                printOneColumn(resultSet, "aid:");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution4(){
        try {
            System.out.println("为居住在Duluth和Kyoto的所有客户订购过同一种商品的经销商的编号:");
            ResultSet resultSet = execute("SELECT o1.oid FROM Orders o1, Customers c1 " +
                    "WHERE o1.cid=c1.cid AND (c1.city='Duluth' or c1.city='Kyoto')" +
                    "AND NOT EXISTS (SELECT * FROM Customers c2 " +
                    "WHERE (c2.city='Duluth' OR " +
                    "c2.city='Kyoto') AND c2.cid NOT in (SELECT o2.cid FROM Orders o2 WHERE " +
                    "o2.aid=o1.aid AND o2.pid=o1.pid)" +
                    ")");

            printOneColumn(resultSet, "aid:");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution5(){
        System.out.println("仅通过a03和a05两个经销商订购过商品的客户编号: ");
        try {
            ResultSet resultSet = execute("SELECT o1.cid" +
                    " from Orders o1 WHERE o1.cid NOT IN (SELECT o2.cid FROM Orders o2 WHERE " +
                    "o2.aid<>'a03' AND o2.aid<>'a05')");
            printOneColumn(resultSet,"cid:");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution6(){

        try {
            ResultSet resultSet =execute("SELECT pid FROM products P WHERE NOT EXISTS " +
                    "(SELECT  * FROM Customers C WHERE C.city='Dallas' AND C.cid NOT  IN (SELECT O.cid FROM " +
                    "Orders O WHERE O.pid=P.pid))");
            System.out.println("居住在Dallas的所有客户都订购过的商品编号:");
            printOneColumn(resultSet, "pid");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution7(){
        System.out.println();
        try {
            System.out.println("享有最高佣金比率的经销商:");
            ResultSet resultSet = execute("SELECT * FROM Agents WHERE percent IN  (SELECT " +
                    "MAX(a.percent) FROM Agents a)");
            printOneColumn(resultSet,"aid:");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void solution8(){
        try {
            System.out.println("仅仅通过a04号经销商订购过商品的客户编号，并给出每个客户的订购总金额: ");
            ResultSet resultSet = execute("SELECT O1.cid, sum(O1.dollars)" +
                    "FROM Orders O1 " +
                    "WHERE O1.cid NOT IN (" +
                    "SELECT O2.cid FROM Orders O2 WHERE O2.aid<>'a04')" +
                    "GROUP BY O1.cid");
            while (resultSet.next()){
                System.out.println("cid: " + resultSet.getString(1) + "total: " + resultSet.getString(2));

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution9(){
        System.out.println("为居住在Duluth的所有客户订购过商品的经销商的编号及其佣金百分比，并按照佣金百分比的降序输出查询结果:");
        try {
            ResultSet resultSet = execute("SELECT aid, percent FROM Agents A " +
                    "WHERE NOT EXISTS (" +
                    "SELECT * FROM Customers C " +
                    "WHERE C.city='Duluth' AND C.cid NOT IN (SELECT O.cid FROM Orders O WHERE " +
                    "O.aid=A.aid))" +
                    "ORDER BY percent DESC ");
            while (resultSet.next()){
                System.out.println("aid: " + resultSet.getString(1) + " " + "percent " + resultSet.getString(2));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void solution10(){
        System.out.println("符合下述条件的商品的编号：至少有一个客户通过与该客户位于同一个城市的经销商订购过该商品:");
        try {
            ResultSet resultSet = execute("SELECT o.pid FROM Orders o, Customers c, Agents a WHERE " +
                    "o.cid=c.cid AND o.aid=a.aid AND c.city=a.city");
            System.out.println(resultSet.getString(-1));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void update1(){
        System.out.println("在Agents表中插入一条percent的取值为空的元组，然后再用IS NULL谓词查出该新插入的元组，" +
                "最后用COMMIT命令提交本次插入操作的执行结果:");
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Agents VALUES ('a07', 'py', 'Nanjing', NULL)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Agents WHERE ISNULL(percent)");
            while (resultSet.next()){
                System.out.println("aid: " + resultSet.getString(1) + " aname: " +
                        resultSet.getString(2) + " city: " + resultSet.getString(3) + " percent: "
                        +resultSet.getString(4));
            }
            statement.execute("COMMIT");

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void update2(){
        System.out.println("在agents表中删除名字为Gray的供应商元组，然后再用ROLLBACK命令恢复被删除的Gray元组:");
        try {

            Statement statement = connection.createStatement();
            statement.execute("start TRANSACTION");
            statement.executeUpdate("DELETE FROM Agents WHERE aname='Gray'");
            System.out.println("删除成功...");
            statement.execute("ROLLBACK ");
            System.out.println("回滚");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void update3(){
        System.out.println("在agents表中，将所有供应商的percent值提升10%，然后再查出所有供应商的信息:");
        try {

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Agents SET percent=percent*1.1 ");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Agents");
            while (resultSet.next()){
                System.out.println("aid: " + resultSet.getString(1) + " " + "aname: " +
                        resultSet.getString(2) + " " + "city: " + resultSet.getString(3)
                        + " " + "percent: " + resultSet.getString(4));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void deleteAll(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE MyCAP.Agents");
            System.out.println("Agents已删除...");
            statement.execute("DROP TABLE MyCAP.Customers");
            System.out.println("Customers已删除...");
            statement.execute("DROP TABLE MyCAP.Orders");
            System.out.println("Orders已删除...");
            statement.execute("DROP TABLE MyCAP.products");
            System.out.println("products已删除...");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
    /*    Assignment1 assignment1 = new Assignment1();
        assignment1.init();*/
          String s="create table web(web_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                  "date  datetime,\n"+     //联网开始时间
                  "user_id INT,\n"+    //联网人
                  "isLocal INT,\n"+    //是否本地
                  "flow INT\n"+            // 使用流量数量（单位：兆）
                  ")DEFAULT CHARSET = utf8;";
          System.out.println(s);
      /*  assignment1.createAll();
        assignment1.insert();
        assignment1.solution1();
        assignment1.solution2();
        assignment1.solution3();
        assignment1.solution4();
        assignment1.solution5();
        assignment1.solution6();
        assignment1.solution7();
        assignment1.solution8();
        assignment1.solution9();
        assignment1.solution10();
        assignment1.update1();
        assignment1.update2();
        assignment1.update3();
        assignment1.deleteAll();*/
    }



    private void printOneColumn(ResultSet resultSet, String title){
        try {
            if(!resultSet.next())
                System.out.println("查询结果为空...");
            else {
                while (resultSet.next()) {
                    System.out.println(title + " " + resultSet.getString(1));
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ResultSet execute(String sql){
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


}