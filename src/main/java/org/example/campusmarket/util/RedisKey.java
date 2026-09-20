package org.example.campusmarket.util;

public class RedisKey {

    public static String product(Long id){

        return "product:" + id;

    }

}