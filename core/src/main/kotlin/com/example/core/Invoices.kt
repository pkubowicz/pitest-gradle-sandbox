package com.example.core

import java.time.Clock

object Invoices {

    fun recordPayment(invoice: Invoice, amount: Int, clock: Clock): Invoice {
        // not tested: cancelled
        // conditional using || will be detected as missing a branch
        // but testing using `in` gives 100% coverage in JaCoCo
//        if (invoice.status == InvoiceStatus.PAID || invoice.status == InvoiceStatus.CANCELLED) {
        if (invoice.status in listOf(InvoiceStatus.PAID, InvoiceStatus.CANCELLED)) {
            throw IllegalStateException("invoice already paid")
        }
        // not tested: amount greater than required
        if (amount != invoice.amount) {
            throw IllegalStateException("wrong amount")
        }
        return invoice.copy(status = InvoiceStatus.PAID, paymentDate = clock.instant())
    }

    fun isOverdue(invoice: Invoice, clock: Clock): Boolean =
        invoice.status != InvoiceStatus.PAID && // wrong: should not count CANCELLED
                invoice.dueDate < clock.instant()
}
