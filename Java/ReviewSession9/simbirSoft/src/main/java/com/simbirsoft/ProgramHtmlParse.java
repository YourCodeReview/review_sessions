package com.simbirsoft;

import com.simbirsoft.testwork.model.Program;

public class ProgramHtmlParse {
    public static void main(String[] args) {
        Program program = new Program("https://www.simbirsoft.com/career/");
        program.startProgram();
    }
}
