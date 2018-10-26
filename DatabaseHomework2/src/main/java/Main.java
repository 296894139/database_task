import java.sql.Timestamp;

/**
 * @创建人 徐介晖
 * @创建时间 2018/10/26
 * @描述
 */
public class Main {
    public static void main(String[]args){
        Mobile_operator operator=new Mobile_operator();
        long start=0;
        long end=0;
        long time;

        start=System.currentTimeMillis();
        //模拟操作一：为电话号码为166587634176的用户订购套餐1，立即生效
        operator.PackageOrder("166587634176",1,1);
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);

        start=System.currentTimeMillis();
        //模拟操作二：为电话号码为166587634176的用户订购套餐2，下月生效
        operator.PackageOrder("166587634176",2,0);
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);

        start=System.currentTimeMillis();
        //模拟操作三：为电话号码为166587634176的用户查询订购套餐记录
        operator.searchPackage("166587634176");
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);

        start=System.currentTimeMillis();
        //模拟操作四：为电话号码为166587634176的用户退订套餐1
        operator.unsubscribe("166587634176",1);
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);

        start=System.currentTimeMillis();
        //模拟操作五：为电话号码为166587634176(id号为1)的用户拨打电话至172473900544（id号为2）,此电话为本地电话
        operator.call_cost(2,1,2,1,new Timestamp(System.currentTimeMillis()));
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);


        start=System.currentTimeMillis();
        //模拟操作六：电话号码为166587634176(id号为1)的用户本地上网花费流量5兆
        operator.web_cost(new Timestamp(System.currentTimeMillis()),1,5,1);
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);

        start=System.currentTimeMillis();
        //模拟操作七：搜索电话号码为166587634176(id号为1)的用户在2018-10的账单
        operator.getBill(1,"2018-10");
        end=System.currentTimeMillis();
        time=end-start;
        System.out.println("操作时间："+time);



    }
}
