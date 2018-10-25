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
       ArrayList<String> list=getPhoneNumbers(500);
       for(int i=0;i<500;i++){
           operator.AddUser(list.get(i),0,0,0,0,0,0,0,0,0,0,0.5,2,1,5,0.1,5000);
       }
       //添加电话记录
       for(int i=0;i<500;i++){
           ArrayList<String> list0=new ArrayList<>();
           //每个人都打给过10个人
           for(int u=0;u<10;u++){
              String to_phone= list.get((int)(Math.random()*500));
              if(!list0.contains(to_phone)){
                  Calendar cal=Calendar.getInstance();
                  int year=cal.get(Calendar.YEAR);
                  int month=cal.get(Calendar.MONTH )+1;
                  int day=(int)(Math.random()*27)+1;
                  int hour=(int)(Math.random()*24);
                  int minute=(int)(Math.random()*60);
                  int second=(int)(Math.random()*60);
                  String date="'"+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"'";
                  double time=(Math.random()*3)+1;
                  operator.AddPhone(time,list.get(i),to_phone,(int)(Math.random()*2),time*0.5,date);
                  list0.add(to_phone);
              }else{
                  u--;
              }
           }
       }
       //添加上网记录
       for(int i=0;i<500;i++){
           for(int u=0;u<(int)(Math.random()*20)+1;u++){
               Calendar cal=Calendar.getInstance();
               int year=cal.get(Calendar.YEAR);
               int month=cal.get(Calendar.MONTH )+1;
               int day=(int)(Math.random()*27)+1;
               int hour=(int)(Math.random()*24);
               int minute=(int)(Math.random()*60);
               int second=(int)(Math.random()*60);
               String date="'"+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"'";
               double megabyte=(Math.random()*3)+1;
               int islocal=(int)(Math.random()*2);
               operator.AddWeb(date,list.get(i),megabyte,islocal,(2*islocal+3)*megabyte);
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
              //  System.out.println(tem);
                list.add(tem);
            }else{
                i--;
            }
        }
        return  list;
    }

    public static void main(String[]args){
          //数据库初始化  时长大约1-2分钟

            new Initialization();


    }
}
