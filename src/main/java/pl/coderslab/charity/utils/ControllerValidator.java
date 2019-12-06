package pl.coderslab.charity.utils;

import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Pattern;

public class ControllerValidator {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)(?=.*[@#$%^&+=!]).{8,}$";

    public static void validateEmail(ModelAndView modelAndView){

    }

    public static boolean validatePasswords(ModelAndView modelAndView, String password1, String password2){
        if (!Pattern.matches(PASSWORD_REGEX, password1)){
            modelAndView.addObject("passwordPatternError", "Hasło musi zawierać min. 8 znaków w tym: wielkie litery, małe litery, cyfry i znaki specjalne");
            return false;
        }
        if (!password1.equals(password2)){
            modelAndView.addObject("passwordEqualsError", "Podane hasła nie są zgodne");
            return false;
        }
        return true;
    }


}
