package com.example.data

import com.example.core.Invoice
import com.example.core.InvoiceStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.Instant

@SpringBootTest
internal class InvoiceServiceTest(
    private val invoiceRepository: InvoiceRepository,
    private val invoiceService: InvoiceService
) {

    @Test
    fun `returns invoice with recorded payment`() {
        invoiceRepository.save(Invoice("inv1", 123, Instant.now())).block()
        val result = invoiceService.recordPayment(PaymentRequest("inv1", BigDecimal.valueOf(1.23)))
            .block()!!
        assertThat(result.status).isEqualTo(InvoiceStatus.PAID)
    }

    // does not test the updated invoice is really saved

    @Test
    fun `rejects payment request with negative amount`() {
        assertThatThrownBy {
            invoiceService.recordPayment(PaymentRequest("inv2", BigDecimal.valueOf(-1)))
        }
    }

}
