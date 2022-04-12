package ru.job4j.concurrent.thread.ThreadExample;

public class WgetSecond {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Starting loading......");
                        for (int i = 0; i < 100; i++) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(1000);
                            System.out.println("\r");
                            System.out.println(Thread.currentThread().getState());
                            System.out.println("\r");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
