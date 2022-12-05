package com.date.tingting.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class getUniversityFromEmailTest {

    @Test
    void get() {
        String value = "skyview9957@khu.ac.kr";
        Pattern pattern = Pattern.compile("[@](.*?)[.]");
        Matcher matcher = pattern.matcher(value);

        while (matcher.find()) {  // 일치하는 게 있다면
            System.out.println(matcher.group(1));

            if(matcher.group(1) ==  null)
                break;
        }
    }
}