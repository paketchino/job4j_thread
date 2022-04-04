package ru.job4j.concurrent.io;

import java.io.*;

public final class ParserFile {

    private File file;

    public ParserFile(File file) {
        synchronized (ParserFile.class) {
            this.file = file;
        }
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
