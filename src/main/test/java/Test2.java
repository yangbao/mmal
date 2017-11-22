import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by u6035457 on 9/20/2017.
 */
public class Test2 {
    // Verify string, must be as number or float, allow negative/positive
    public static final String isNumberExp = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
    public static void main(String[] args) {
        String strTime = "20170918 20:05:37 538000000";
        long millionSecond = Integer.valueOf(isNumeric(strTime.substring(18)) ? strTime.substring(18) : "0");
        System.out.println(millionSecond);
    }
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(isNumberExp);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
