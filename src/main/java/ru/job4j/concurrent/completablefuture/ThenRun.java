package ru.job4j.concurrent.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static ru.job4j.concurrent.completablefuture.RunAsync.goToTrash;
import static ru.job4j.concurrent.completablefuture.RunAsync.iWork;
import static ru.job4j.concurrent.completablefuture.SupplyAsync.buyProduct;

public class ThenRun {

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын: я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын: Я помыл руки");
        });
        iWork();
    }

    static class ThenAccept {

        public static void thenAcceptExample() throws Exception {
            CompletableFuture<String> bm = buyProduct("Молоко");
            bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
            iWork();
            System.out.println("Куплено: " + bm.get());
        }
    }

    static class ThenApply {

        public static void thenApplyExample() throws Exception {
            CompletableFuture<String> bm = buyProduct("Молоко")
                    .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
            iWork();
            System.out.println(bm.get());
        }
    }

    static class ThenComposeAndCombine {

        public static void thenComposeExample() throws Exception {
            CompletableFuture<Void> result = buyProduct("Молоко").thenCompose(a -> goToTrash());
            iWork();
        }

        public static void thenCombineExample() throws Exception {
            CompletableFuture<String> result = buyProduct("Молоко")
                    .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
            iWork();
            System.out.println(result.get());
        }
    }

    static class AllOfAndAnyOf {

        public static CompletableFuture<Void> washHands(String name) {
            return CompletableFuture.runAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + ", моет руки");
            });
        }

        public static void allOfExample() throws Exception {
            CompletableFuture<Void> all = CompletableFuture.allOf(
                    washHands("Папа"), washHands("Мама"),
                    washHands("Ваня"), washHands("Боря")
            );
            TimeUnit.SECONDS.sleep(3);
        }


        public static void anyOfExample() throws Exception {
            CompletableFuture<Object> first = CompletableFuture.anyOf(
                    washHands("Папа"), washHands("Мама"),
                    washHands("Ваня"), washHands("Боря")
            );
            System.out.println("Кто сейчас моет руки?");
            TimeUnit.SECONDS.sleep(1);
            System.out.println(first.get());
        }
    }

}
