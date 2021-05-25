package com.example.core

import java.time.Instant

data class PaymentEntity(
    val timeStarted: Instant,
    val timeFinished: Instant,
    val amount: Int,
    val id: String,
)

data class PaymentView(
    val timeStarted: String,
    val timeFinished: String,
    val amount: Int,
)

object PaymentConverter {
    fun toView(entity: PaymentEntity) =
        PaymentView(
            timeStarted = entity.timeStarted.toString(),
            timeFinished = entity.timeStarted.toString(), // copy-paste!
            amount = entity.amount,
        )
}
