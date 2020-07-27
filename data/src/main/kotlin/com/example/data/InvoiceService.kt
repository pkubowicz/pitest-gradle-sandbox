package com.example.data

import com.example.core.Invoice
import com.example.core.Invoices
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.Clock

@Service
class InvoiceService(private val invoiceRepository: InvoiceRepository) {
    private val clock = Clock.systemUTC()

    fun recordPayment(paymentRequest: PaymentRequest): Mono<Invoice> {
        validate(paymentRequest)
        return invoiceRepository.findById(paymentRequest.invoiceId)
            // missing test for ID not found, but 100% coverage as this is not counted as branch
            .switchIfEmpty(Mono.error(IllegalStateException("no invoice with ID ${paymentRequest.invoiceId}")))
            .flatMap { invoice ->
                val updated = Invoices.recordPayment(invoice, convertAmount(paymentRequest.amount), clock)
                invoiceRepository.save(invoice) // outdated invoice saved!
                    .then(Mono.just(updated))
            }
    }

    private fun validate(paymentRequest: PaymentRequest) {
        if (paymentRequest.amount < BigDecimal.ONE) { // should be zero
            throw IllegalArgumentException("amount has to be positive")
        }
    }

    private fun convertAmount(amount: BigDecimal): Int =
        (amount * BigDecimal.valueOf(100)).intValueExact()
}

data class PaymentRequest(
    val invoiceId: String,
    val amount: BigDecimal
)
