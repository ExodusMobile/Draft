package com.development.kernel.draft;



import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Utility {
    //Email Pattern
    private static final String EMAIL_PATTERN = "";
            //"^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@"   + "[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";

    public static boolean validate(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    static boolean isNotNull(String txt){
        return txt != null && txt.trim().length() > 0;
    }
}