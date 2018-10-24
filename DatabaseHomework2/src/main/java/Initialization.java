import java.util.ArrayList;
import java.util.Calendar;


/**
 * @创建人 徐介晖
 * @创建时间 2018/10/23
 * @描述
 */
public class Initialization {
    Mobile_operator operator=new Mobile_operator();
   private  Initialization(){
         //初始化  1.建立所有表格   2.添加相应数据
         operator.createTable();
         //添加套餐   可自行再度添加
       operator.AddPackage(20,100,0,0,0,0,0.5,2,0.5,5,0.1);  //语音套餐
       operator.AddPackage(10,0,0,0,0,200,0.5,2,0.5,5,0.1);  //短信套餐
       operator.AddPackage(20,0,2000,0,0,200,0.5,2,0.5,3,0.1);  //本地流量套餐
       operator.AddPackage(30,0,0,0,2000,200,0.5,2,0.5,3,0.1);  //全国流量套餐
       //添加用户
       ArrayList<String> list=getPhoneNumbers(5000);
       for(int i=0;i<5000;i++){
           operator.AddUser(list.get(i),0,0,0,0,0,0,0,0,0,0,0.5,2,1,5,0.1,5000);
       }
       //添加电话记录
       for(int i=0;i<5000;i++){
           ArrayList<String> list0=new ArrayList<>();
           //每个人都打给过10个人
           for(int u=0;u<10;u++){
              String to_phone= list.get((int)(Math.random()*5000));
              if(!list0.contains(to_phone)){
                  Calendar cal=Calendar.getInstance();
                  int year=cal.get(Calendar.YEAR);
                  int month=cal.get(Calendar.MONTH )+1;
                  String date="'"+year+"-"+month+日"'";
                  list0.add(to_phone);
              }else{
                  u--;
              }
           }



       }

    }


    public static ArrayList<String> getPhoneNumbers(int n){
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<n;i++){
            String tem="1";
            for(int u=0;u<11;u++){
                tem=tem+(int)(Math.random()*(10-1+1));
            }
            if(!list.contains(tem)){
                System.out.println(tem);
                list.add(tem);
            }else{
                i--;
            }
        }
        return  list;
    }

    public static void main(String[]args){
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH )+1;
          System.out.println(year+"   "+month);
          //new Initialization();
           //getPhoneNumbers(5000);
    }
}
