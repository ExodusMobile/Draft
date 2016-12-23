package com.development.kernel.draft;

/**
 * Created by Acer on 11.12.2016.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which has Utility methods
 *
 */
class Utility {
    //Email Pattern
    private static final String EMAIL_PATTERN = "";
            //"^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@"   + "[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";

    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean validate(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    /**
     * Checks for Null String object
     *
     * @param txt
     * @return true for not null and false for null String object
     */
    static boolean isNotNull(String txt){
        return txt != null && txt.trim().length() > 0;
    }
}