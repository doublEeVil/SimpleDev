package com.simpledev.stonelang._01;

import java.io.File;

public class LexerRunner {
    public static void main(String ... args) throws ParseException{
        File file = new File("D:\\_Data\\SimpleDev\\StoneLang\\src\\main\\java\\com\\simpledev\\stonelang\\_01\\LexerRunner.java");
        Lexer lexer = new Lexer(file);
        //
        for (Token t; (t = lexer.read()) != Token.EOF;) {
            System.out.println("=> type:" + t.type() + " " + t.getText());
        }
    }
}
