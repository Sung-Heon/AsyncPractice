package challenge1.coroutines

import externalLegacyCodeNotUnderOurControl.PriceService
import kotlinx.coroutines.*


/**
 * This example uses Kotlin coroutines.
 *
 * @author Marcus Fihlon, www.fihlon.ch
 */

fun main(args: Array<String>): Unit = runBlocking {

    val nrOfPrices = 10

    val jobs = List(nrOfPrices) {
        async(Dispatchers.Default) {
            PriceService().price
        }
    }

    println(
            jobs.sumBy { it.await() } / nrOfPrices
    )

}
