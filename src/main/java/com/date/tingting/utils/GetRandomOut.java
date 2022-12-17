package com.date.tingting.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRandomOut {


        public static String getRandomStr(int size) {
            if(size > 0) {
                char[] tmp = new char[size];
                for(int i=0; i<tmp.length; i++) {
                    int div = (int) Math.floor( Math.random() * 2 );

                    if(div == 0) { // 0이면 숫자로
                        tmp[i] = (char) (Math.random() * 10 + '0') ;
                    }else { //1이면 알파벳
                        tmp[i] = (char) (Math.random() * 26 + 'A') ;
                    }
                }
                return new String(tmp);
            }
            return "ERROR : Size is required.";
        }

}
