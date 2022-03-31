package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(url)) {
            byte[] dateBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long begin = System.currentTimeMillis();
            while ((bytesRead = in.read(dateBuffer, 0, 1024)) != -1) {
                downloadData = bytesRead;
                if (downloadData < speed) {
                    long finish = System.currentTimeMillis();
                    if ((begin - finish) < 1) {
                        Thread.sleep(1000 - (begin - finish));
                    }
                    downloadData = 0;
                    begin = System.currentTimeMillis();
                }
                fileOutputStream.write(dateBuffer, 0, downloadData);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();

        long start = System.currentTimeMillis();
        System.out.println(start + " : start");
        Thread.sleep(1000);
        long finish = System.currentTimeMillis();
        System.out.println(finish + " : finish");
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
    }
}
