package com.example.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class PaymentConverterTest {
    @Test
    fun `converts entity to view`() {
        // how will you catch regressions if both dates are the same?
        val entity = PaymentEntity(Instant.parse("2021-03-14T15:09:25Z"), Instant.parse("2021-03-14T15:09:25Z"), 1, "1")
        val result = PaymentConverter.toView(entity)

        assertThat(result.timeStarted).isEqualTo("2021-03-14T15:09:25Z")
        assertThat(result.timeFinished).isEqualTo("2021-03-14T15:09:25Z")
        assertThat(result.amount).isEqualTo(1)
    }
}
