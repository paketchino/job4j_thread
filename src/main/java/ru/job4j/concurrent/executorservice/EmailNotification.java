package ru.job4j.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailToUser(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String body = String.format("Add a new event to {%s}", user.getUsername());
                String subject = String.format("Notification {%s} to email {%s}.", user.getUsername(), user.getEmail());
                send(subject, body, user.getEmail());
            }
        });
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
