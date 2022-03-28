package ru.job4j.concurrent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1500);
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                HashMap<Integer, String> str = new HashMap<>();
                str.put(1, "\\");
                str.put(2, "|");
                str.put(3, "/");
                for (Map.Entry entry : str.entrySet()) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + entry.getValue());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
