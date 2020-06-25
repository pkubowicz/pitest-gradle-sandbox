package com.example.core

import java.time.Instant

data class Invoice(
    val id: String,
    val amount: Int,
    val dueDate: Instant,
    val status: InvoiceStatus = InvoiceStatus.WAITING,
    val paymentDate: Instant? = null
)

enum class InvoiceStatus { WAITING, PAID, CANCELLED }
