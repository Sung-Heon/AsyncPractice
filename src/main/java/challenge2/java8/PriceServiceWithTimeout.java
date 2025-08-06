package challenge2.java8;

import externalLegacyCodeNotUnderOurControl.PriceService;

import java.util.concurrent.*;
import static externalLegacyCodeNotUnderOurControl.PrintlnWithThreadname.println;

public class PriceServiceWithTimeout {
    private final PriceService priceService;
    private final long timeout;
    private final TimeUnit timeUnit;
    private final int fallbackValue;
    private final ExecutorService executor;

    public PriceServiceWithTimeout(PriceService priceService, long timeout, TimeUnit timeUnit, int fallbackValue) {
        this.priceService = priceService;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.fallbackValue = fallbackValue;
        this.executor = Executors.newCachedThreadPool();
    }

    public CompletableFuture<Integer> getPriceWithTimeoutAsync() {
        // Create the main CompletableFuture for the price service
        CompletableFuture<Integer> priceFuture = CompletableFuture.supplyAsync(priceService::getPrice, executor);
        
        // Apply timeout to the CompletableFuture
        return priceFuture.completeOnTimeout(fallbackValue, timeout, timeUnit);
    }
    
    public int getPriceWithTimeout() {
        try {
            return getPriceWithTimeoutAsync().join();
        } catch (CompletionException e) {
            // Handle any exceptions that occurred during execution
            println("Error executing price service, using fallback value: " + fallbackValue);
            return fallbackValue;
        }
    }
    
    public int getFallbackValue() {
        return fallbackValue;
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
