package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
        URL urlRef = null;
        try {
            urlRef = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert urlRef != null;
            try (BufferedInputStream in = new BufferedInputStream(urlRef.openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(url)) {
                byte[] dateBuffer = new byte[1024];
                int bytesRead;
                int downloadData = 0;
                long begin = System.currentTimeMillis();
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
                    fileOutputStream.write(dateBuffer, 0, downloadData);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int speed = Integer.parseInt("1048056");
        Thread wget = new Thread(new Wget(args[0], speed));
        wget.start();
        wget.join();
        if (args[0].isEmpty()) {
            throw new IllegalArgumentException("Вы не ввели ссылку для скачивания");
        }
    }
}
