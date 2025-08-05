package challenge1.java8;

import externalLegacyCodeNotUnderOurControl.PriceService;

public class MyAnswer {
    private void run() {
        int price = new PriceService().getPrice();
        System.out.println("Price: " + price);
    }

    public static void main(final String... args) {
        new MyAnswer().run();
    }

}
