package ru.job4j.concurrent.thread.ThreadExample;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1500);
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] strings = {"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 0; i < strings.length; i++) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + strings[i]);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
