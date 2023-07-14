package ru.otus.booksdb.helpers;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceImpl implements IOService {

    private final PrintStream output = System.out;

    private final Scanner input = new Scanner(System.in);

    @Override
    public void outputString(String string) {
        output.println(string);
    }

    @Override
    public String readString() {
        return input.nextLine();
    }
}
