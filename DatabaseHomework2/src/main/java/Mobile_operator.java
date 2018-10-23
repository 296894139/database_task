import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class Mobile_operator {
    private Connection con;
    private Util util;

    public static void main(String[] args) {
        Mobile_operator operator = new Mobile_operator();
        long startTime, endTime;

        /*System.out.println("#2 建表");
        startTime = System.currentTimeMillis();
        operator.createTable();*/
      //  operator.test();
      /*  endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");

        System.out.println("#3 插入数据");
        startTime = System.currentTimeMillis();
        dormitory.insertData();
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");

        System.out.println("#4 查询“王小星”同学所在宿舍楼的所有院系");
        startTime = System.currentTimeMillis();
        dormitory.searchWangXiaoxing();
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");

        System.out.println("#5 陶园一舍的住宿费用提高至 1200 元");
        startTime = System.currentTimeMillis();
        dormitory.updatePrice();
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");

        System.out.println("#6 软件学院男女研究生互换宿舍楼");
        startTime = System.currentTimeMillis();
        dormitory.exchangeBuilding();
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");*/
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
    private void createTable() {
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
    1.用户套餐查询
     */
    private void searchPackage(String phone){
        String statement = "SELECT * \n" +
                "FROM unsubscribe ,user \n" +
                "WHERE  phone=phoneNumber and user.user_id=unsubscribe.user_id;";
        util.executeSQL(statement, con);
    }
     /*
    2 .用户套餐订购
     */
     private void PackageOrder(String phone,int p_id,int istTakeEffect){
         if(istTakeEffect==1){ //选择立即生效的套餐
             String statement ="select basic_cost from package where package.package_id=p_id;";
            ResultSet re= util.executeSQL(statement, con);
            //套餐的价格
             double cost=0;
             try {
                 cost=re.getDouble(1);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
            //账户余额
             statement ="select balance,user_id from user where user.phoneNumber=phone;";
             re= util.executeSQL(statement, con);
             double balance=0;
             int id=0;
             try {
                 balance=re.getDouble(1);
                 id=re.getInt(2);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
            //账户付费
             String result_money=balance-cost+"";
             statement="update user set balance="+result_money+" where user.phoneNumber=phone ;";
             //增加订购记录
             statement="insert into orders (time,take_effect,user_id,package_id) values(NOW(),1,"+id+","+p_id+",);";
             util.executeSQL(statement, con);
             //增加历史记录
             statement="insert into unsubscribe(start_time,user_id,package_id) values(NOW(),"+id+","+p_id+");";
             util.executeSQL(statement, con);
             //更新用户本月免费话费、流量、短信情况和价格
             statement ="select basic_local_phone_time,basic_local_web_flow,basic_roam_phone_time,basic_roam_web_flow,basic_message,local_web_cost,local_phone_cost,roam_phone_cost,roam_web_cost,message_cost  from user where phoneNumber=phone;";
             re= util.executeSQL(statement, con);
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
             try {
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
             statement ="select basic_local_phone_time,basic_local_web_flow,basic_roam_phone_time,basic_roam_web_flow,basic_message,local_web_cost,local_phone_cost,roam_phone_cost,roam_web_cost,message_cost  from package where package.package_id=p_id;";
             re= util.executeSQL(statement, con);
             try {
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
                     "  where phone=phoneNumber"+
                     " ;";
             re= util.executeSQL(statement, con);

         }else{
             //套餐下月生效
             //账户余额
             String statement ="select user_id from user where user.phoneNumber=phone;";
             ResultSet re= util.executeSQL(statement, con);
             int id=0;
             try {
                 id=re.getInt(1);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             //增加订购记录
             statement="insert into orders (time,take_effect,user_id,package_id) values(NOW(),0,"+id+","+p_id+",);";
             util.executeSQL(statement, con);
             //增加历史记录
             statement="insert into unsubscribe(start_time,user_id,package_id) values(NOW(),"+id+","+p_id+");";
             util.executeSQL(statement, con);
             //注意：实际运行过程中，在每月初自动扣除这月套餐的费用,且将update orders表置值1
         }
     }
    /*
   3..用户套餐退订
    */
     private void unsubscribe(String phone,int p_id){
         String statement ="select user_id from user where user.phoneNumber=phone;";
         ResultSet re= util.executeSQL(statement, con);
         int id=0;
         try {
             id=re.getInt(1);
         } catch (SQLException e) {
             e.printStackTrace();
         }
          statement ="delete from orders where user_id = "+id +" and  "+p_id+" =package_id;";
          re= util.executeSQL(statement, con);
          statement="update unsubscribe set end_time =NOW() where package_id="+p_id+" and end_time=NULL;";
         re= util.executeSQL(statement, con);
     }

     /*
   4..用户话费生成
    */
     private void call_cost(double time, int from_id,int to_id,int islocal,Timestamp date){
         String statement ="select used_local_phone_time,used_roam_phone_time,basic_local_phone_time,basic_roam_phone_time,local_phone_cost,roam_phone_cost from user where user_id="+from_id +";";
         ResultSet re=util.executeSQL(statement, con);
       if(islocal==1){
           //此电话为本地电话
           double used_local_phone_time=0;
           double basic_local_phone_time =0;
           double local_phone_cost=0;
           try {
               used_local_phone_time=re.getDouble(1);
               basic_local_phone_time=re.getDouble(3);
               local_phone_cost=re.getDouble(5);
           } catch (SQLException e) {
               e.printStackTrace();
           }
           if(used_local_phone_time+time<=basic_local_phone_time){
               //未超过额定免费时长
               //增加通话记录
               statement="insert into phone(date,from_user,to_user,cost,isLocal,time) values("+date+","+from_id+","+to_id+","+0+","+islocal+","+time+" );";
               util.executeSQL(statement, con);
           }else{
               //超过免费时长   1.账户扣款  2.增加通话记录
               double cost_time=Math.min(used_local_phone_time+time-basic_local_phone_time,time);
               statement="insert into phone(date,from_user,to_user,cost,isLocal,time) values("+date+","+from_id+","+to_id+","+cost_time*local_phone_cost+","+islocal+","+time+" );";
               util.executeSQL(statement, con);
               statement="select balance from user where user_id="+from_id+";";
               re=util.executeSQL(statement, con);
               double balance=0;
               try {
                   balance=re.getDouble(1);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               double re_balance=balance-cost_time*local_phone_cost;
               statement="update user set balance="+re_balance+" where user_id="+from_id+";";
               util.executeSQL(statement, con);
           }

       }else{
           //非本地通话
           double used_roam_phone_time=0;
           double basic_roam_phone_time =0;
           double roam_phone_cost=0;
           try {
               used_roam_phone_time=re.getDouble(1);
               basic_roam_phone_time=re.getDouble(3);
               roam_phone_cost=re.getDouble(5);
           } catch (SQLException e) {
               e.printStackTrace();
           }
           if(used_roam_phone_time+time<=basic_roam_phone_time){
               //未超过额定免费时长
               //增加通话记录
               statement="insert into phone(date,from_user,to_user,cost,isLocal,time) values("+date+","+from_id+","+to_id+","+0+","+islocal+","+time+" );";
               util.executeSQL(statement, con);

           }else{
               //超过免费时长   1.账户扣款  2.增加通话记录
               double cost_time=Math.min(used_roam_phone_time+time-basic_roam_phone_time,time);
               statement="insert into phone(date,from_user,to_user,cost,isLocal,time) values("+date+","+from_id+","+to_id+","+cost_time*roam_phone_cost+","+islocal+","+time+" );";
               util.executeSQL(statement, con);
               statement="select balance from user where user_id="+from_id+";";
               re=util.executeSQL(statement, con);
               double balance=0;
               try {
                   balance=re.getDouble(1);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               double re_balance=balance-cost_time*roam_phone_cost;
               statement="update user set balance="+re_balance+" where user_id="+from_id+";";
               util.executeSQL(statement, con);
           }
       }
     }
    /*
    5.用户网费生成
     */
    private void web_cost(Timestamp date,int u_id,double megabyte,int islocal){
        String statement ="select used_local_web_flow,used_roam_web_flow,basic_local_web_flow,basic_roam_web_flow,local_web_cost,roam_web_cost   from user where user_id="+u_id +";";
        ResultSet re=util.executeSQL(statement, con);
        if(islocal==1){
            //本地上网
            double used_local_web_flow=0;
            double basic_local_web_flow =0;
            double local_web_cost=0;
            try {
                used_local_web_flow=re.getDouble(1);
                basic_local_web_flow=re.getDouble(3);
                local_web_cost=re.getDouble(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(used_local_web_flow+megabyte<=basic_local_web_flow){
                //未超过额定免费流量
                //增加上网记录
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date+","+u_id+","+0+","+islocal+","+megabyte+" );";
                util.executeSQL(statement, con);

            }else{
                //超过免费时长   1.账户扣款  2.增加上网记录
                double cost_time=Math.min(used_local_web_flow+megabyte-basic_local_web_flow,megabyte);
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date+","+u_id+","+cost_time*local_web_cost+","+islocal+","+megabyte+" );";
                util.executeSQL(statement, con);
                statement="select balance from user where user_id="+u_id+";";
                re=util.executeSQL(statement, con);
                double balance=0;
                try {
                    balance=re.getDouble(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                double re_balance=balance-cost_time*local_web_cost;
                statement="update user set balance="+re_balance+" where user_id="+u_id+";";
                util.executeSQL(statement, con);
            }
        }else{
            //异地上网
            double used_roam_web_flow=0;
            double basic_roam_web_flow=0;
            double roam_web_cost=0;
            try {
                used_roam_web_flow=re.getDouble(1);
                basic_roam_web_flow=re.getDouble(3);
                roam_web_cost=re.getDouble(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(used_roam_web_flow+megabyte<=basic_roam_web_flow){
                //未超过额定免费流量
                //增加上网记录
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date+","+u_id+","+0+","+islocal+","+megabyte+" );";
                util.executeSQL(statement, con);

            }else{
                //超过免费时长   1.账户扣款  2.增加上网记录
                double cost_time=Math.min(used_roam_web_flow+megabyte-basic_roam_web_flow,megabyte);
                statement="insert into web(date,user_id,cost,isLocal,flow) values("+date+","+u_id+","+cost_time*roam_web_cost+","+islocal+","+megabyte+" );";
                util.executeSQL(statement, con);
                statement="select balance from user where user_id="+u_id+";";
                re=util.executeSQL(statement, con);
                double balance=0;
                try {
                    balance=re.getDouble(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                double re_balance=balance-cost_time*roam_web_cost;
                statement="update user set balance="+re_balance+" where user_id="+u_id+";";
                util.executeSQL(statement, con);
            }
        }
    }
    /*
    6.账单生成
     */
    private void getBill(int u_id,String date){
        //此处的date 为所需查询的年月  结构为  xxxx-xx  例：2018-10
        String statement="select * from  phone where from_user_id="+u_id+"  and date_format(date,'%Y-%m')="+date+";";
        ResultSet re=util.executeSQL(statement, con);
        while(re!=null){
            try {
                System.out.println("id:"+re.getInt(1)+"  拨打时间："+re.getTimestamp(2)+" 拨打人:"+re.getInt(3)+" 接收人:"+re.getInt(4)+" 费用："+re.getDouble(5));
            }catch (Exception e){
                System.out.println(e);
            }

        }
        statement ="select * from  web  where user_id="+u_id+"  and date_format(date,'%Y-%m')="+date+";";
        re=util.executeSQL(statement, con);
        while(re!=null){
            try {
                System.out.println("id:"+re.getInt(1)+"  开始联网时间："+re.getTimestamp(2)+" 联网人:"+re.getInt(3)+" 费用："+re.getDouble(4)+" 使用流量："+re.getDouble(6)+"兆");
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }


}