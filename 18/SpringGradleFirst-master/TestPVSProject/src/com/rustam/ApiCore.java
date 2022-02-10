package com.rustam;

public class ApiCore {

    public static void main(String[] args) {
        ApiCore apiCore = new ApiCore();
        System.out.println(apiCore.str());
    }

    public String str() {

        ApiCore apiCore = new ApiCore();
        int count = 0;
        String msg = "World";
        return "Hello" + msg + apiCore.header();

    }

    public String header() {
        return "\nI love you baby  ";
    }

}
