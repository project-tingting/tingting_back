package com.date.tingting.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUniversityFromEmail {

    public static String get(String userEmail){

        Pattern pattern = Pattern.compile("[@](.*?)[.]");
        Matcher matcher = pattern.matcher(userEmail);

        while (matcher.find()) {  // 일치하는 게 있다면
            return matcher.group(1);
        }

        return null;
    }

}
