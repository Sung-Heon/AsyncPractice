package challenge2.java8;

import externalLegacyCodeNotUnderOurControl.PriceService;
import java.util.concurrent.*;

import static externalLegacyCodeNotUnderOurControl.PrintlnWithThreadname.println;

/**
 * This class demonstrates how to use PriceServiceWithTimeout to handle timeouts
 * and fallback values when the price service is too slow.
 */
public class PriceServiceWithTimeoutDemo {
    
    public static void main(String[] args) {
        // Create a slow price service that takes 3 seconds to respond
        PriceService slowPriceService = new PriceService(3); // 3 seconds delay
        
        // Create our timeout wrapper with 2 seconds timeout and fallback value 42
        PriceServiceWithTimeout priceService = new PriceServiceWithTimeout(
                slowPriceService, 
                2, 
                TimeUnit.SECONDS, 
                42
        );
        
        try {
            println("Requesting price with 2 seconds timeout...");
            int price = priceService.getPriceWithTimeout();
            println("Received price: " + price);
            
            // The price service takes 3 seconds, but we timeout at 2 seconds,
            // so we should see the fallback value 42
            
        } finally {
            priceService.shutdown();
        }
        
        // Let's also demonstrate a case where the service responds in time
        PriceService fastPriceService = new PriceService(1); // 1 second delay
        PriceServiceWithTimeout fastPriceServiceWithTimeout = new PriceServiceWithTimeout(
                fastPriceService,
                2,
                TimeUnit.SECONDS,
                42
        );
        
        try {
            println("\nRequesting price from faster service (1s) with 2 seconds timeout...");
            int price = fastPriceServiceWithTimeout.getPriceWithTimeout();
            println("Received price: " + price);
            // This should show the actual price, not the fallback
        } finally {
            fastPriceServiceWithTimeout.shutdown();
        }
    }
}
