package ru.job4j.concurrent.buffer;

import java.io.*;
import java.util.function.Predicate;

public class ReadFile {

    private File file;

    public ReadFile(File file) {
        this.file = file;
    }

    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) > 0) {
                output.append((char) data);
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContent() throws IOException {
        return content(file -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return content(date -> date < 0x80);
    }

}
