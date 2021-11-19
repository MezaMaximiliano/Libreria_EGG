package com.libreryV3.librery30.Validation;

import com.libreryV3.librery30.Entitys.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    public static boolean nameValidation(String name){

        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();

    }

    public static boolean emailValidation(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher match = pattern.matcher(email);

        return match.find();
    }
}

