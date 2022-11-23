package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.v1.Parser;
import main.java.v1.Reader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        StringBuilder buff = new StringBuilder();
        try {
            final BufferedReader scan = new BufferedReader(new FileReader("C:\\Users\\DRAG\\Desktop\\test.tll"));
            int byt = 0;
            while ((byt = scan.read()) != -1) {
                buff.append((char) byt) ;
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String code = buff.toString().trim();
        Reader reader = new Reader(code);
        reader.start();
//         Display all tokens
//        reader.tokenList.forEach(token -> {
//            System.out.print(token + " | ");
//        });
//        System.out.println();
        Parser parser = new Parser(reader.tokenList);
        parser.start();
    }
}

/*
 variable<number> someNumber = 10;
 variable<string> someName = "Atharva"
 function<nothing> name()

 variable<array<number>> ar = [];
 someName = repeat<nothing> start<10>(
      print "name"
      someName = name + ","
 )

*/
