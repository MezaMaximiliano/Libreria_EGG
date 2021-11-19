package com.libreryV3.librery30.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorialValidation {

    public static boolean nameValidation(String name){
        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name.trim());
        return matcher.find();
    }
}
