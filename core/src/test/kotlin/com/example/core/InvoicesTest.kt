package com.example.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class InvoicesTest {
    @Test
    fun `can pay more`() {
        assertThat(Invoices.canPayFrom(Account(10), 1)).isTrue()
    }

    @Test
    fun `cannot pay less`() {
        assertThat(Invoices.canPayFrom(Account(10), 20)).isFalse()
    }

    @Test
    fun `marks waiting invoice paid if amount is sufficient`() {
        val result = Invoices.recordPayment(
            Invoice("i1", 150, someDueDate()),
            150,
            Clock.systemUTC()
        )
        assertThat(result.status).isEqualTo(InvoiceStatus.PAID)
    }

    @Test
    fun `records payment date for correct payment`() {
        val paymentInstant = Instant.parse("2020-05-21T13:51:00Z")
        val result = Invoices.recordPayment(
            Invoice("i1", 150, someDueDate()),
            150,
            Clock.fixed(paymentInstant, ZoneId.of("UTC"))
        )
        assertThat(result.paymentDate).isEqualTo(paymentInstant)
    }

    @Test
    fun `throws an exception if amount is insufficient`() {
        assertThatThrownBy {
            Invoices.recordPayment(
                Invoice("i1", 150, someDueDate()),
                145,
                Clock.systemUTC()
            )
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("wrong amount")
    }

    @Test
    fun `throws an exception if invoice is already paid`() {
        assertThatThrownBy {
            Invoices.recordPayment(
                Invoice("i1", 150, someDueDate(), InvoiceStatus.PAID, Instant.now()),
                150,
                Clock.systemUTC()
            )
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("already paid")
    }

    @TestFactory
    fun `recognises overdue invoice`(): List<DynamicTest>  {
        val dueDate = Instant.parse("2020-05-01T00:00:00Z")
        return listOf(
            Pair("2020-04-13T13:57:00Z", false),
            Pair("2020-06-18T12:42:58Z", true),
            Pair("2020-05-01T07:12:05Z", true)
        ).map { (nowDate, expectedResult) ->
            DynamicTest.dynamicTest("recognises overdue invoice when now is $nowDate") {
                assertThat(
                    Invoices.isOverdue(
                        Invoice("i1", 614, dueDate),
                        Clock.fixed(Instant.parse(nowDate), ZoneId.of("UTC"))
                    )
                ).isEqualTo(expectedResult)
            }
        }

    }

    private fun someDueDate() = Instant.parse("2020-12-31T12:01:00Z")
}
