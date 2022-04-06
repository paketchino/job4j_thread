package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
            try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(
                     Paths.get(new URI(url).getPath()).getFileName().toString())) {
            byte[] dateBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long begin = System.currentTimeMillis();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    while ((bytesRead = in.read(dateBuffer, 0, 1024)) != -1) {
                        downloadData += bytesRead;
                        if (downloadData >= speed) {
                            long finish = System.currentTimeMillis();
                            if ((finish - begin) < 1000) {
                                Thread.sleep(1000 - (finish - begin));
                            }
                            downloadData = 0;
                            begin = System.currentTimeMillis();
                        }
                        fileOutputStream.write(dateBuffer, 0, bytesRead);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread wget = new Thread(new Wget(args[0], Integer.parseInt(args[1])));
        wget.start();
        wget.join();
        wget.interrupt();
        if (args[0].isEmpty()) {
            throw new IllegalArgumentException("Вы не ввели ссылку для скачивания");
        }
        if (args[1].isEmpty()) {
            throw new IllegalArgumentException("В параметрах запуска отсуствует скорость скачивания");
        }
    }
}
