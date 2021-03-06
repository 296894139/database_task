import java.sql.*;
import java.util.*;
/**
 * @创建人 徐介晖
 * @创建时间 2018/10/23
 * @描述
 */
public class Mobile_operator {
    private Connection con;
    private Util util;

    public static void main(String[] args) {
        Mobile_operator operator = new Mobile_operator();

        long start=0,end=0;
        start=System.currentTimeMillis();
        operator.PackageOrder("18251835786",4,1);
        end=System.currentTimeMillis();
        long tem=end-start;
        System.out.println("操作时间："+tem);


       /* System.out.println("#2 建表");
        //startTime = System.currentTimeMillis();
        operator.createTable();*/
      /* operator.AddPackage(20,100,0,0,0,0,0.5,2,0.5,5,0.1);  //语音套餐
       operator.AddPackage(10,0,0,0,0,200,0.5,2,0.5,5,0.1);  //短信套餐
        operator.AddPackage(20,0,2000,0,0,200,0.5,2,0.5,3,0.1);  //本地流量套餐
        operator.AddPackage(30,0,0,0,2000,200,0.5,2,0.5,3,0.1);  //全过流量套餐*/
       // operator.AddUser("18251835786",0,0,0,0,0,0,0,0,0,0,0.5,2,0.5,5,0.1,100);
    }
    public void test(){
        String s="select basic_cost from package where package_id=1;";

        ResultSet re=null;

        try (Statement statement2 = con.createStatement()) {

            re=statement2.executeQuery(s);
            re.next();
            System.out.println("tem"+re.getDouble(1));
        }catch(Exception e){
            System.out.println("error");
        }
    }

    public Mobile_operator() {
        try {
            DBConnection dbConnection = new DBConnection();
            con = dbConnection.getConnection();
            con.setAutoCommit(false);

            util = new Util();
        } catch (Exception e) {
            System.out.println("fail");
            e.printStackTrace();
        }
    }
/*
    建表
 */
    public void createTable() {
        String createUser="create table user (user_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "phoneNumber char(20),\n"+
                "used_local_phone_time DOUBLE,\n"+    //本地电话时常使用量 （单位：分钟）
                "used_local_web_flow DOUBLE,\n"+    //本地流量使用数量（单位：兆）
                "used_roam_phone_time DOUBLE,\n"+    //漫游电话时常使用量 （单位：分钟）
                "used_roam_web_flow DOUBLE,\n"+    //漫游流量使用数量（单位：兆）
                "used_message  INT,\n"+//短信数量
                "basic_local_phone_time DOUBLE,\n"+    //基本本地免费电话时常 （单位：分钟）
                "basic_local_web_flow DOUBLE,\n"+    //基本本地免费流量数量（单位：兆）
                "basic_roam_phone_time DOUBLE,\n"+    //基本漫游免费电话时常 （单位：分钟）
                "basic_roam_web_flow DOUBLE,\n"+    //基本漫游免费流量数量（单位：兆）
                "basic_message INT,\n"+    //基本免费短信数量（单位：个）
                "local_phone_cost  DOUBLE,\n"+  //本地电话每分钟费用
                "local_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "roam_phone_cost  DOUBLE,\n"+  //漫游电话每分钟费用
                "roam_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "message_cost  DOUBLE,\n"+//短信费用
                "balance DOUBLE\n"+    //账户余额
                ")DEFAULT CHARSET = utf8;";
        String createPackage="create table package(package_id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "basic_cost DOUBLE,\n"+   //每月基本费用
                "basic_local_phone_time DOUBLE,\n"+    //基本本地免费电话时常 （单位：分钟）
                "basic_local_web_flow DOUBLE,\n"+    //基本本地免费流量数量（单位：兆）
                "basic_roam_phone_time DOUBLE,\n"+    //基本漫游免费电话时常 （单位：分钟）
                "basic_roam_web_flow DOUBLE,\n"+    //基本漫游免费流量数量（单位：兆）
                "basic_message INT,\n"+    //基本免费短信数量（单位：个）
                "local_phone_cost  DOUBLE,\n"+  //本地电话每分钟费用
                 "local_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "roam_phone_cost  DOUBLE,\n"+  //漫游电话每分钟费用
                "roam_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "message_cost  DOUBLE\n"+//短信费用

                ")DEFAULT CHARSET = utf8;";
 /*订购套餐*/   String createOrder="create table orders(order_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "time  DATETIME,\n"+
                "take_effect  INT,\n"+  //(是否立即生效（1立即生效 0 月初生效）)
                "user_id INT,\n"+
                "package_id INT\n"+
                ")DEFAULT CHARSET = utf8;";
 /*历史订购记录*/       String createUnsubscribe="create table unsubscribe(unsubscribe_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "start_time  datetime,\n"+
                "end_time  datetime,\n"+
                "user_id INT,\n"+
                "package_id INT\n"+
                ")DEFAULT CHARSET = utf8;";
        String createPhone="create table phone(phone_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "date  datetime,\n"+     //电话开始时间
                "from_user_id INT,\n"+    //拨打人
                "to_user_id INT,\n"+      //接收人
                "cost DOUBLE,\n"+      //费用
                "isLocal INT,\n"+    //是否本地
                "time INT\n"+            //持续时间
                ")DEFAULT CHARSET = utf8;";
        String createWeb="create table web(web_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "date  datetime,\n"+     //联网开始时间
                "user_id INT,\n"+    //联网人
                "cost DOUBLE,\n"+      //费用
                "isLocal INT,\n"+    //是否本地
                "flow DOUBLE\n"+            // 使用流量数量（单位：兆）
                ")DEFAULT CHARSET = utf8;";
        String[] statements = {createUser,createPackage, createOrder,createUnsubscribe,createPhone,createWeb};
        util.executeSQLs(statements, con);
    }
    /*
    添加电话记录
     */
    public void AddPhone(double time,String from_phone,String to_phone,int islocal,double cost,String date ){
        String statement="select user_id from user where phoneNumber="+from_phone+";";
        ResultSet re=null;
        try (Statement statement2 = con.createStatement()) {
            re=statement2.executeQuery(statement);
            re.next();
            int from_id=re.getInt(1);
            statement="select user_id from user where phoneNumber="+to_phone+";";
            re=statement2.executeQuery(statement);
            re.next();
            int to_id=re.getInt(1);
            statement="insert into phone(date,from_user_id,to_user_id,cost,isLocal,time) values("+date+","+from_id+","+to_id+","+cost+","+islocal+","+time+" );";
            util.executeSQLNoResult(statement,con);
        }catch(Exception e){
            System.out.println("error");
        }
    }
    /*
    添加上网记录
     */
    public void AddWeb(String date,String phone,double megabyte,int islocal,double cost){
        String statement="select user_id from user where phoneNumber="+phone+";";
        ResultSet re=null;
        try (Statement statement2 = con.createStatement()) {
            re=statement2.executeQuery(statement);
            re.next();
            int id=re.getInt(1);
            statement="insert into web(date,user_id,cost,isLocal,flow) values("+date+","+id+","+cost+","+islocal+","+megabyte+" );";
            util.executeSQLNoResult(statement,con);
        }catch(Exception e){
            System.out.println("error");
        }
    }
/*
添加套餐
 */
public void AddPackage(double bc,double blpt,double blwf,double  brpt,double brwf,int bm ,double lpc,double lwc,double rpc,double rwc,double mc ){
        String package1="insert into package values(0,"+bc+","+blpt+","+blwf+","+brpt+","+brwf+","+bm+","+lpc+","+lwc+","+rpc+","+rwc+","+mc+" );";
     /*         "basic_cost DOUBLE,\n"+   //每月基本费用
                "basic_local_phone_time DOUBLE,\n"+    //基本本地免费电话时常 （单位：分钟）
                "basic_local_web_flow DOUBLE,\n"+    //基本本地免费流量数量（单位：兆）
                "basic_roam_phone_time DOUBLE,\n"+    //基本漫游免费电话时常 （单位：分钟）
                "basic_roam_web_flow DOUBLE,\n"+    //基本漫游免费流量数量（单位：兆）
                "basic_message INT,\n"+    //基本免费短信数量（单位：个）
                "local_phone_cost  DOUBLE,\n"+  //本地电话每分钟费用
                "local_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "roam_phone_cost  DOUBLE,\n"+  //漫游电话每分钟费用
                "roam_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "message_cost  DOUBLE\n"+//短信费用*/

        util.executeSQLNoResult(package1,con);
    }
/*
添加用户
 */
 public void AddUser(String p,double ulpt,double ulwf,double urpt,double urwf,int um,double blpt,double blwf,double brpt,double brwf,int bm,double lpc,double lwc,double rpc,double rwc,double mc,double b){
        String statement="insert into user values(0,"+p+","+ulpt+","+ulwf+","+urpt+","+urwf+","+um+","+blpt+","+blwf+","+brpt+","+brwf+","+bm+","+lpc+","+lwc+","+rpc+","+rwc+","+mc+","+b+");";
        util.executeSQLNoResult(statement,con);
        /*
          (user_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"+
                "phoneNumber char(20),\n"+
                "used_local_phone_time DOUBLE,\n"+    //本地电话时常使用量 （单位：分钟）
                "used_local_web_flow DOUBLE,\n"+    //本地流量使用数量（单位：兆）
                "used_roam_phone_time DOUBLE,\n"+    //漫游电话时常使用量 （单位：分钟）
                "used_roam_web_flow DOUBLE,\n"+    //漫游流量使用数量（单位：兆）
                "used_message  INT,\n"+//短信数量
                "basic_local_phone_time DOUBLE,\n"+    //基本本地免费电话时常 （单位：分钟）
                "basic_local_web_flow DOUBLE,\n"+    //基本本地免费流量数量（单位：兆）
                "basic_roam_phone_time DOUBLE,\n"+    //基本漫游免费电话时常 （单位：分钟）
                "basic_roam_web_flow DOUBLE,\n"+    //基本漫游免费流量数量（单位：兆）
                "basic_message INT,\n"+    //基本免费短信数量（单位：个）
                "local_phone_cost  DOUBLE,\n"+  //本地电话每分钟费用
                "local_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "roam_phone_cost  DOUBLE,\n"+  //漫游电话每分钟费用
                "roam_web_cost  DOUBLE,\n"+//本地流量每兆费用
                "message_cost  DOUBLE,\n"+//短信费用
                "balance DOUBLE\n"+    //账户余额


         */
 }
    /*
    1.用户套餐查询
     */
   public void searchPackage(String phone){
        String statement = "SELECT * \n" +
                "FROM unsubscribe ,user \n" +
                "WHERE "+ phone+"=phoneNumber and user.user_id=unsubscribe.user_id;";
        ResultSet re=null;
        try (Statement statement2 = con.createStatement()){
            re=statement2.executeQuery(statement);

            while(re.next()){
                System.out.println("用户电话："+phone+"  开始时间："+re.getTimestamp(2)+"  结束时间："+re.getTimestamp(3)+" 套餐编号："+re.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
     /*
    2 .用户套餐订购
     */
    public void PackageOrder(String phone,int p_id,int istTakeEffect){
         if(istTakeEffect==1){ //选择立即生效的套餐
             ResultSet re=null;
             String statement ="select basic_cost from package where package.package_id="+p_id+";";

            // re= util.executeSQL(statement, con);


            //套餐的价格
             double cost=0;
             try (Statement statement2 = con.createStatement()) {
                 re=statement2.executeQuery(statement);
                 re.next();
                 cost=re.getDouble(1);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
            //账户余额
             statement ="select balance,user_id from user where user.phoneNumber="+phone+";";
           //  re= util.executeSQL(statement, con);
             double balance=0;
             int id=0;
             try (Statement statement2 = con.createStatement()) {
                 re=statement2.executeQuery(statement);
                 re.next();
                 balance=re.getDouble(1);
                 id=re.getInt(2);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
            //账户付费
             String result_money=balance-cost+"";
             statement="update user set balance="+result_money+" where user.phoneNumber="+phone +";";
             util.executeSQLNoResult(statement,con);
             //增加订购记录
             statement="insert into orders (time,take_effect,user_id,package_id) values(NOW(),1,"+id+","+p_id+");";
             util.executeSQLNoResult(statement, con);
             //增加历史记录
             statement="insert into unsubscribe(start_time,user_id,package_id) values(NOW(),"+id+","+p_id+");";
             util.executeSQLNoResult(statement, con);
             //更新用户本月免费话费、流量、短信情况和价格
             statement ="select basic_local_phone_time,basic_local_web_flow,basic_roam_phone_time,basic_roam_web_flow,basic_message,local_web_cost,local_phone_cost,roam_phone_cost,roam_web_cost,message_cost  from user where phoneNumber="+phone+";";
             double basic_local_phone_time=0;
             double    basic_local_web_flow=0;
             double basic_roam_phone_time=0;
             double basic_roam_web_flow=0;
             int basic_message=0;
             double local_web_cost=0;
             double local_phone_cost=0;
             double roam_phone_cost=0;
             double roam_web_cost=0;
             double message_cost=0;
             try (Statement statement2 = con.createStatement()) {
                 re=statement2.executeQuery(statement);
                 re.next();
                basic_local_phone_time=re.getDouble(1);
                basic_local_web_flow=re.getDouble(2);
                basic_roam_phone_time=re.getDouble(3);
                basic_roam_web_flow=re.getDouble(4);
                basic_message=re.getInt(5);
                local_web_cost=re.getDouble(6);
                local_phone_cost=re.getDouble(7);
                roam_phone_cost=re.getDouble(8);
                roam_web_cost=re.getDouble(9);
                message_cost=re.getDouble(10);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             statement ="select basic_local_phone_time,basic_local_web_flow,basic_roam_phone_time,basic_roam_web_flow,basic_message,local_web_cost,local_phone_cost,roam_phone_cost,roam_web_cost,message_cost  from package where package.package_id="+p_id+";";
            // re= util.executeSQL(statement, con);
             try (Statement statement2 = con.createStatement()){
                 re=statement2.executeQuery(statement);
                 re.next();
                 basic_local_phone_time+=re.getDouble(1);
                 basic_local_web_flow+=re.getDouble(2);
                 basic_roam_phone_time+=re.getDouble(3);
                 basic_roam_web_flow+=re.getDouble(4);
                 basic_message+=re.getInt(5);
                 local_web_cost=Math.min(re.getDouble(6),local_web_cost);
                 local_phone_cost=Math.min(re.getDouble(7),local_phone_cost);
                 roam_phone_cost=Math.min(re.getDouble(8),roam_phone_cost);
                 roam_web_cost=Math.min(re.getDouble(9),roam_web_cost);
                 message_cost=Math.min(re.getDouble(10),message_cost);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             statement ="update user set" +
                     "  basic_local_phone_time="+basic_local_phone_time+
                     ",  basic_roam_phone_time="+basic_roam_phone_time+
                     ",   basic_local_web_flow="+ basic_local_web_flow+
                     ",    basic_roam_web_flow="+  basic_roam_web_flow+
                     ",   basic_message="+  basic_message+
                     ",    local_web_cost="+  local_web_cost+
                     ",    local_phone_cost="+  local_phone_cost+
                     ",    roam_phone_cost="+  roam_phone_cost+
                     ",  roam_web_cost="+  roam_web_cost+
                     ",   message_cost="+  message_cost+
                     "  where "+ phone+"=phoneNumber"+
                     " ;";
              util.executeSQLNoResult(statement, con);

         }else{
             //套餐下月生效
             //账户余额
             ResultSet re=null;
             String statement ="select user_id from user where user.phoneNumber="+phone+";";
              re= util.executeSQL(statement, con);
             int id=0;
             try  (Statement statement2 = con.createStatement()){
                 re=statement2.executeQuery(statement);
                 re.next();
                 id=re.getInt(1);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             //增加订购记录
             statement="insert into orders (time,take_effect,user_id,package_id) values(NOW(),0,"+id+","+p_id+");";
             util.executeSQLNoResult(statement, con);
             //增加历史记录
             statement="insert into unsubscribe(start_time,user_id,package_id) values(NOW(),"+id+","+p_id+");";
             util.executeSQLNoResult(statement, con);
             //注意：实际运行过程中，在每月初自动扣除这月套餐的费用,且将update orders表置值1
         }
     }
    /*
   3..用户套餐退订
    */
   public void unsubscribe(String phone,int p_id){
         String statement ="select user_id from user where user.phoneNumber="+phone+";";
         ResultSet re=null;
          re= util.executeSQL(statement, con);
         int id=0;
         try (Statement statement2 = con.createStatement()){
             re=statement2.executeQuery(statement);
             re.next();
             id=re.getInt(1);
         } catch (SQLException e) {
             e.printStackTrace();
         }
          statement ="delete from orders where user_id = "+id +" and  "+p_id+" =package_id;";
          util.executeSQLNoResult(statement, con);
          statement="update unsubscribe set end_time =NOW() where package_id="+p_id+" and end_time is null and user_id="+id+";";
          util.executeSQLNoResult(statement, con);
     }

     /*
   4..用户话费生成
    */
    public void call_cost(double time, int from_id,int to_id,int islocal,Timestamp date){
         String statement ="select used_local_phone_time,used_roam_phone_time,basic_local_phone_time,basic_roam_phone_time,local_phone_cost,roam_phone_cost from user where user_id="+from_id +";";
         ResultSet re=null;
         // re=util.executeSQL(statement, con);
       if(islocal==1){
           //此电话为本地电话
           double used_local_phone_time=0;
           double basic_local_phone_time =0;
           double local_phone_cost=0;
           try (Statement statement2 = con.createStatement()){
               re=statement2.executeQuery(statement);
               re.next();
               used_local_phone_time=re.getDouble(1);
               basic_local_phone_time=re.getDouble(3);
               local_phone_cost=re.getDouble(5);
           } catch (SQLException e) {
               e.printStackTrace();
           }
           if(used_local_phone_time+time<=basic_local_phone_time){
               //未超过额定免费时长
               //增加通话记录
               //增加本地通话时长
               String str_date="'"+date+"'";
               statement="insert into phone(date,from_user_id,to_user_id,cost,isLocal,time) values( "+str_date+","+from_id+","+to_id+","+0+","+islocal+","+time+");";
               util.executeSQLNoResult(statement, con);
               double phone_time=used_local_phone_time+time;
               statement="update user set used_local_phone_time="+phone_time+" where user.user_id="+from_id+";";
               util.executeSQLNoResult(statement, con);

           }else{
               //超过免费时长   1.账户扣款  2.增加通话记录   3.增加本地通话时长
               double cost_time=Math.min(used_local_phone_time+time-basic_local_phone_time,time);
               String date_str="'"+date+"'";
               statement="insert into phone(date,from_user_id,to_user_id,cost,isLocal,time) values("+date_str+","+from_id+","+to_id+","+cost_time*local_phone_cost+","+islocal+","+time+" );";

               util.executeSQLNoResult(statement, con);
               statement="select balance from user where user_id="+from_id+";";
               double balance=0;
               try (Statement statement2 = con.createStatement()){
                   re=statement2.executeQuery(statement);
                   re.next();
                   balance=re.getDouble(1);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               double re_balance=balance-cost_time*local_phone_cost;
               statement="update user set balance="+re_balance+" where user_id="+from_id+";";
               util.executeSQLNoResult(statement, con);
               double phone_time=used_local_phone_time+time;
               statement="update user set used_local_phone_time="+phone_time+" where user.user_id="+from_id+";";
               util.executeSQLNoResult(statement, con);

           }

       }else{
           //非本地通话
           statement ="select used_local_phone_time,used_roam_phone_time,basic_local_phone_time,basic_roam_phone_time,local_phone_cost,roam_phone_cost from user where user_id="+from_id +";";
           double used_roam_phone_time=0;
           double basic_roam_phone_time =0;
           double roam_phone_cost=0;
           try (Statement statement2 = con.createStatement()){
               re=statement2.executeQuery(statement);
               re.next();
               used_roam_phone_time=re.getDouble(2);
               basic_roam_phone_time=re.getDouble(4);
               roam_phone_cost=re.getDouble(6);
           } catch (SQLException e) {
               e.printStackTrace();
           }
           if(used_roam_phone_time+time<=basic_roam_phone_time){
               //未超过额定免费时长
               //增加通话记录
               //增加外地通话时长
               String date_str="'"+date+"'";
               statement="insert into phone(date,from_user_id,to_user_id,cost,isLocal,time) values("+date_str+","+from_id+","+to_id+","+0+","+islocal+","+time+" );";
               util.executeSQLNoResult(statement, con);
               double phone_time=used_roam_phone_time+time;
               statement="update user set used_roam_phone_time="+phone_time+" where user.user_id="+from_id+";";
               util.executeSQLNoResult(statement, con);


           }else{
               //超过免费时长   1.账户扣款  2.增加通话记录 3.增加外地通话时长
               double cost_time=Math.min(used_roam_phone_time+time-basic_roam_phone_time,time);
               String date_str="'"+date+"'";
               statement="insert into phone(date,from_user_id,to_user_id,cost,isLocal,time) values("+date_str+","+from_id+","+to_id+","+cost_time*roam_phone_cost+","+islocal+","+time+" );";
               util.executeSQLNoResult(statement, con);
               statement="select balance from user where user_id="+from_id+";";
             //  re=util.executeSQL(statement, con);
               double balance=0;
               try (Statement statement2 = con.createStatement()){
                   re=statement2.executeQuery(statement);
                   re.next();
                   balance=re.getDouble(1);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               double re_balance=balance-cost_time*roam_phone_cost;
               statement="update user set balance="+re_balance+" where user_id="+from_id+";";
               util.executeSQLNoResult(statement, con);
               double phone_time=used_roam_phone_time+time;
               statement="update user set used_roam_phone_time="+phone_time+" where user.user_id="+from_id+";";
               util.executeSQLNoResult(statement, con);
           }
       }
     }
    /*
    5.用户网费生成
     */
    public void web_cost(Timestamp date,int u_id,double megabyte,int islocal){
        ResultSet re=null;
        String statement ="select used_local_web_flow,used_roam_web_flow,basic_local_web_flow,basic_roam_web_flow,local_web_cost,roam_web_cost   from user where user_id="+u_id +";";
        if(islocal==1){
            //本地上网
            double used_local_web_flow=0;
            double basic_local_web_flow =0;
            double local_web_cost=0;
            try (Statement statement2 = con.createStatement()){
                re=statement2.executeQuery(statement);
                re.next();
                used_local_web_flow=re.getDouble(1);
                basic_local_web_flow=re.getDouble(3);
                local_web_cost=re.getDouble(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(used_local_web_flow+megabyte<=basic_local_web_flow){
                //未超过额定免费流量
                //增加上网记录
                String date_str="'"+date+"'";
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date_str+","+u_id+","+0+","+islocal+","+megabyte+" );";
                util.executeSQLNoResult(statement, con);
                double local_web=used_local_web_flow+megabyte;
                statement="update user set used_local_web_flow="+local_web+" where user_id="+u_id+";";
                util.executeSQLNoResult(statement, con);

            }else{
                //超过免费时长   1.账户扣款  2.增加上网记录
                double cost_time=Math.min(used_local_web_flow+megabyte-basic_local_web_flow,megabyte);
                String date_str="'"+date+"'";
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date_str+","+u_id+","+cost_time*local_web_cost+","+islocal+","+megabyte+" );";
                util.executeSQLNoResult(statement, con);
                statement="select balance from user where user_id="+u_id+";";
              //  re=util.executeSQL(statement, con);
                double balance=0;
                try (Statement statement2 = con.createStatement()){
                    re=statement2.executeQuery(statement);
                    re.next();
                    balance=re.getDouble(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                double re_balance=balance-cost_time*local_web_cost;
                statement="update user set balance="+re_balance+" where user_id="+u_id+";";
                util.executeSQLNoResult(statement, con);
                double local_web=used_local_web_flow+megabyte;
                statement="update user set used_local_web_flow="+local_web+" where user_id="+u_id+";";
                util.executeSQLNoResult(statement, con);
            }
        }else{
            //异地上网
            statement ="select used_local_web_flow,used_roam_web_flow,basic_local_web_flow,basic_roam_web_flow,local_web_cost,roam_web_cost   from user where user_id="+u_id +";";
            double used_roam_web_flow=0;
            double basic_roam_web_flow=0;
            double roam_web_cost=0;
            try (Statement statement2 = con.createStatement()){
                re=statement2.executeQuery(statement);
                re.next();
                used_roam_web_flow=re.getDouble(1);
                basic_roam_web_flow=re.getDouble(3);
                roam_web_cost=re.getDouble(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(used_roam_web_flow+megabyte<=basic_roam_web_flow){
                //未超过额定免费流量
                //增加上网记录
                String date_str="'"+date+"'";
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date_str+","+u_id+","+0+","+islocal+","+megabyte+" );";
                util.executeSQLNoResult(statement, con);
                double roam_web=used_roam_web_flow+megabyte;
                statement="update user set used_roam_web_flow="+roam_web+" where user_id="+u_id+";";
                util.executeSQLNoResult(statement, con);

            }else{
                //超过免费时长   1.账户扣款  2.增加上网记录
                double cost_time=Math.min(used_roam_web_flow+megabyte-basic_roam_web_flow,megabyte);
                String date_str="'"+date+"'";
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date_str+","+u_id+","+cost_time*roam_web_cost+","+islocal+","+megabyte+" );";
                util.executeSQLNoResult(statement, con);
                statement="select balance from user where user_id="+u_id+";";
                re=util.executeSQL(statement, con);
                double balance=0;
                try  (Statement statement2 = con.createStatement()){
                    re=statement2.executeQuery(statement);
                    re.next();
                    balance=re.getDouble(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                double re_balance=balance-cost_time*roam_web_cost;
                statement="update user set balance="+re_balance+" where user_id="+u_id+";";
                util.executeSQLNoResult(statement, con);
                double roam_web=used_roam_web_flow+megabyte;
                statement="update user set used_roam_web_flow="+roam_web+" where user_id="+u_id+";";
                util.executeSQLNoResult(statement, con);
            }
        }
    }
    /*
    6.账单生成
     */
  public void getBill(int u_id,String date){
        //此处的date 为所需查询的年月  结构为  xxxx-xx  例：2018-10
        date="'"+date+"'";
        String statement="select * from  phone where from_user_id="+u_id+"  and date_format(date,'%Y-%m')="+date+";";
       // System.out.println(statement);
        ResultSet re=null;
            try (Statement statement2 = con.createStatement()){
                re=statement2.executeQuery(statement);
                while(re.next()){
                    System.out.println("id:"+re.getInt(1)+"  拨打时间："+re.getTimestamp(2)+" 拨打人:"+re.getInt(3)+" 接收人:"+re.getInt(4)+" 费用："+re.getDouble(5));

                }
             }catch (Exception e){
                System.out.println(e);
            }

        statement ="select * from  web  where user_id="+u_id+"  and date_format(date,'%Y-%m')="+date+";";


            try (Statement statement2 = con.createStatement()){
                re=statement2.executeQuery(statement);
               while(re.next()){
                   System.out.println("id:"+re.getInt(1)+"  开始联网时间："+re.getTimestamp(2)+" 联网人:"+re.getInt(3)+" 费用："+re.getDouble(4)+" 使用流量："+re.getDouble(6)+"兆");

               }
              }catch (Exception e){
                System.out.println(e);
            }

    }
   /*
   7.月初操作（模拟每月初自动扣款和套餐生效）
    */
   public void month_start(){

       String statement="update user set used_local_phone_time=0, used_local_web_flow=0,used_roam_phone_time=0,used_roam_web_flow=0,used_message=0,basic_local_phone_time=0,basic_local_web_flow=0,basic_roam_phone_time=0,basic_roam_web_flow=0,local_phone_cost=0.5,local_web_cost=3,roam_phone_cost=0.5,roam_web_cost=5;";
       util.executeSQLNoResult(statement,con);
       ResultSet re=null;
       statement="select take_effect,user_id,package_id from order;";
       try (Statement statement2 = con.createStatement()) {
           ArrayList<Integer> u_ids=new ArrayList<>();
           ArrayList<Integer>  p_ids=new ArrayList<>();
           re=statement2.executeQuery(statement);
          while(re.next()){
               int e=re.getInt(1);
               int u_id=re.getInt(2);
               int p_id=re.getInt(3);
               u_ids.add(u_id);
               p_ids.add(p_id);
               if(e==0){
                   statement="update order set take_effect=1 where user_id="+u_id+" and package_id="+p_id+";";
                   util.executeSQLNoResult(statement,con);
               }
          }
          int length=u_ids.size();
          for(int i=0;i<length;i++){
              statement ="select basic_cost from package where package.package_id="+p_ids.get(i)+";";
              re=statement2.executeQuery(statement);
              re.next();
              //套餐价格
              double cost=re.getDouble(1);
              statement ="select balance from user where user_id="+u_ids.get(i)+";";
              re=statement2.executeQuery(statement);
              re.next();
              //用户余额
              double balance=re.getDouble(1);
              //用户付费
              String result_money=balance-cost+"";
              statement="update user set balance="+result_money+" where user_id="+u_ids.get(i)+";";
              util.executeSQLNoResult(statement,con);
              //更新用户本月免费话费、流量、短信情况和价格
              statement ="select basic_local_phone_time,basic_local_web_flow,basic_roam_phone_time,basic_roam_web_flow,basic_message,local_web_cost,local_phone_cost,roam_phone_cost,roam_web_cost,message_cost  from user where user_id="+u_ids.get(i)+";";
              re=statement2.executeQuery(statement);
              re.next();
             double basic_local_phone_time=re.getDouble(1);
             double basic_local_web_flow=re.getDouble(2);
             double basic_roam_phone_time=re.getDouble(3);
             double basic_roam_web_flow=re.getDouble(4);
             double basic_message=re.getInt(5);
             double local_web_cost=re.getDouble(6);
             double local_phone_cost=re.getDouble(7);
             double roam_phone_cost=re.getDouble(8);
             double roam_web_cost=re.getDouble(9);
             double message_cost=re.getDouble(10);
              statement ="select basic_local_phone_time,basic_local_web_flow,basic_roam_phone_time,basic_roam_web_flow,basic_message,local_web_cost,local_phone_cost,roam_phone_cost,roam_web_cost,message_cost  from package where package.package_id="+p_ids.get(i)+";";
              re=statement2.executeQuery(statement);
              re.next();
              basic_local_phone_time+=re.getDouble(1);
              basic_local_web_flow+=re.getDouble(2);
              basic_roam_phone_time+=re.getDouble(3);
              basic_roam_web_flow+=re.getDouble(4);
              basic_message+=re.getInt(5);
              local_web_cost=Math.min(re.getDouble(6),local_web_cost);
              local_phone_cost=Math.min(re.getDouble(7),local_phone_cost);
              roam_phone_cost=Math.min(re.getDouble(8),roam_phone_cost);
              roam_web_cost=Math.min(re.getDouble(9),roam_web_cost);
              message_cost=Math.min(re.getDouble(10),message_cost);
              statement ="update user set" +
                      "  basic_local_phone_time="+basic_local_phone_time+
                      ",  basic_roam_phone_time="+basic_roam_phone_time+
                      ",   basic_local_web_flow="+ basic_local_web_flow+
                      ",    basic_roam_web_flow="+  basic_roam_web_flow+
                      ",   basic_message="+  basic_message+
                      ",    local_web_cost="+  local_web_cost+
                      ",    local_phone_cost="+  local_phone_cost+
                      ",    roam_phone_cost="+  roam_phone_cost+
                      ",  roam_web_cost="+  roam_web_cost+
                      ",   message_cost="+  message_cost+
                      "  where "+ p_ids.get(i)+"=user_id"+
                      " ;";
              util.executeSQLNoResult(statement, con);
          }
       }catch(Exception e){
           System.out.println("error");
       }

   }

}