package it15ns.friendscom.model;

/**
 * Created by valentin on 5/9/17.
 */

public class FormTools {
    public static boolean isValidPassword(String password){
        //TODO: testen hab ich einfach aus google
        String ePattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(password);


        return password.length() > 4;
        //^                 # start-of-string
        //(?=.*[0-9])       # a digit must occur at least once
        //(?=.*[a-z])       # a lower case letter must occur at least once
        //(?=.*[A-Z])       # an upper case letter must occur at least once
        //(?=.*[@#$%^&+=])  # a special character must occur at least once
        //(?=\S+$)          # no whitespace allowed in the entire string
        //.{8,}             # anything, at least eight places though
        //$                 # end-of-string
    }
    public static boolean isValidMailAdress(String mailAdress){
        //TODO: testen hab ich einfach aus google
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(mailAdress);
        return m.matches();
    }
    public static boolean isValidNickname(String nickname){
        //TODO: kp, einfach mehr als 4 zeichen?
        //TODO: bzw evtl. hier server anfragen ob der user vorhanden ist?
        if(nickname.length() > 3){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isValidMobileNumber(String mobileNumber){
        //TODO: kp, einfach mehr als 8 zahlen?
        String ePattern = "^[0-9]*.{8,}$";   //number only and more than 8 digits
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(mobileNumber);
        return m.matches();
    }
    public static boolean isValidBirthday(String birthday){
        //TODO: testen hab ich einfach aus google
        String ePattern = "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(birthday);
        return m.matches();
    }
    public static boolean hashPassword(String password){
        //TODO:
        return true;
    }
    public static boolean doPasswordsMatch(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }else{
            return false;
        }
    }
}
