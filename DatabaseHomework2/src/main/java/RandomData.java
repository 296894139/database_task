import java.util.ArrayList;


/**
 * @创建人 徐介晖
 * @创建时间 2018/10/23
 * @描述
 */
public class RandomData {
    public String[] getPhoneNumbers(int n){
        String []result=new String[n];
        for(int i=0;i<n;i++){
            result[i]="1";
            for(int u=0;u<11;u++){
                result[i]=result[i]+(int)(Math.random()*(10-1+1));
            }
            System.out.println(result[i]);

        }
        return  result;
    }

    public static void main(String[]args){

        RandomData r=new RandomData();
        r.getPhoneNumbers(10);
    }
}
