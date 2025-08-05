package challenge1.java8;

import externalLegacyCodeNotUnderOurControl.PriceService;

import java.util.concurrent.CompletableFuture;

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
        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
        for (int i = 0; i < 100; i++) {
            System.out.println("2. 비동기 작업 시작 후, join 전 - " + i);
        }
        //워커쓰레드꺼 먼저 찍히는거 확인.
        // supplyASync하자마자 던진다.

        System.out.println("4. Price: " + future.join());
    }

    public static void main(final String... args) {
        new MyAnswer().runAsync();
    }

}
