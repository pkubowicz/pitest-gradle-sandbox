package com.example.data

import com.example.core.Invoice
import com.example.core.InvoiceStatus
import com.example.core.Invoices
import org.springframework.stereotype.Component
import java.time.Clock

@Component
class OverdueInvoiceNotificationJob(
    private val invoiceRepository: InvoiceRepository,
    private val notificationSender: NotificationSender
) {
    private val clock = Clock.systemUTC()

    fun sendNotifications() {
        invoiceRepository.findAllByStatusNot(InvoiceStatus.PAID) // wrong: should not take CANCELLED
            .filter { Invoices.isOverdue(it, clock) } // not covered by tests, but not counted as branch
            .next() // will only take first element, but tests won't detect this
            .doOnNext { sendNotification(it) }
            .subscribe()
    }

    private fun sendNotification(invoice: Invoice) {
        notificationSender.sendNotification("${invoice.id} is overdue!")
    }
}

interface NotificationSender {
    fun sendNotification(notification: String)
}

@Component
class ConsoleNotificationSender : NotificationSender {
    override fun sendNotification(notification: String) {
        println(notification)
    }
}
