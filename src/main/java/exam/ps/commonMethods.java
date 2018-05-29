package exam.ps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class commonMethods {

    public static boolean dateEquals(Date date1, Date date2){

        if (date1.getYear() == date2.getYear()) {
            if (date1.getMonth() == date2.getMonth()) {
                if (date1.getDate() == date2.getDate()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSessionInvalid(HttpServletRequest request){
        HttpSession session = request.getSession();//

        return session.getAttribute("user") == null;
    }
}
