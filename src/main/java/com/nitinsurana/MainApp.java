package com.nitinsurana;

public class MainApp {

    public static void main(String... args) throws Exception {
        if (args.length != 1) {
            System.out.println("Correct Usage is : ");
            System.out.println("java MainApp <path to file or directory containing urls>");
            return;
        }
        UrlFileProcessor urlFileProcessor = new UrlFileProcessor(args[0]);
        Thread t = urlFileProcessor.start();
        t.wait();
    }
}

