package challenge1.java8;

import externalLegacyCodeNotUnderOurControl.PriceService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAnswer {
    private void run() {
        int price = new PriceService().getPrice();
        System.out.println("Price: " + price);
    }

    private void runAsync() {
        System.out.println("1. 비동기 작업 시작 전");
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("3. 비동기 작업 실행 중 - " + Thread.currentThread().getName());
            return new PriceService().getPrice();
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("2. 비동기 작업 시작 후, join 전 - " + i);
        }
        //워커쓰레드꺼 먼저 찍히는거 확인.
        // supplyASync하자마자 던진다.

        System.out.println("4. Price: " + future.join());
    }

    private void runAsync2() {
        System.out.println("1. 비동기 작업 시작 전");
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("3. 비동기 작업 실행 중 - " + Thread.currentThread().getName());
            return new PriceService().getPrice();
        });
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("3. 비동기 작업 실행 중 - " + Thread.currentThread().getName());
            return new PriceService().getPrice();
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("3. 비동기 작업 실행 중 - " + Thread.currentThread().getName());
            return new PriceService().getPrice();
        });

        //각기 새로운 워커쓰레드에 할당된다. 그런데 공통풀에 할당됨.
        int price = future.join() + future1.join() + future2.join();

        System.out.println("4. Price: " + price / 3);
    }


    private void runAsync3() {
        System.out.println("1. 비동기 작업 시작 전");
        ExecutorService customPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                    return new PriceService().getPrice();
                }, customPool
        );

        //각기 새로운 워커쓰레드에 할당된다. 그런데 공통풀에 할당됨.
       int price2 = future.thenApply(price -> {
            System.out.println("4. Price: " + price / 3);
            return price;
        }).thenApply(price -> {
            System.out.println("4. Price: " + price / 3);
            return price;
        }).thenApply(price -> {
            System.out.println("4. Price: " + price / 3);
            return price;
        }).join();
    }

    public static void main(final String... args) {
        new MyAnswer().runAsync3();
    }

}
