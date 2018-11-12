package other;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FunTest {
	
	static void modifyTheValue(int valueToBeOperated, Function<Integer, String> function){

        String newValue = function.apply(valueToBeOperated);

        System.out.println(newValue);

    }

    public static void main(String[] args) {

//        int incr = 20;  int myNumber = 10;
//
//        //val-> val + incr
//        //相当于定义了一个方法：
//        //        int function(int val) {
//        //            return val+incr;
//        //        }
//        modifyTheValue(myNumber, val-> (val + incr)+"");
//
    	
    	
        
    }
}
