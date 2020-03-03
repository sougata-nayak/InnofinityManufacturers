package com.example.innofinitymanufacturers;

import java.util.Random;

public class UVID {

    Random rand = new Random();
    int n = rand.nextInt(1000);

    public String sendUvid(){

        n++;
        String s = String.valueOf(n);
        return s;
    }

}
