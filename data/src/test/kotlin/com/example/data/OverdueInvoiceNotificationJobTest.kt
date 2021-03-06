package com.example.data

import com.example.core.Invoice
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Instant

// or: how to misuse Mockito and pretend your code is tested
internal class OverdueInvoiceNotificationJobTest {

    private val invoiceRepository = mock<InvoiceRepository>()
    private val notificationSender = mock<NotificationSender>()
    private val overdueInvoiceNotificationJob = OverdueInvoiceNotificationJob(invoiceRepository, notificationSender)

    @Test
    fun `sends notifications`() {
        whenever(invoiceRepository.findAllByStatusNotIn(any())).thenReturn(
            Flux.just(Invoice("i1", 151, Instant.now())) // poor test: just 1 invoice, all invoices overdue
        )

        overdueInvoiceNotificationJob.sendNotifications()

        verify(invoiceRepository).findAllByStatusNotIn(any()) // not testing if criteria correct
        verify(notificationSender).sendNotification(any()) // not testing text
    }
}
