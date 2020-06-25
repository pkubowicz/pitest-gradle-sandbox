package com.example.data

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class ConsoleNotificationSenderTest {

    @Test
    fun `does not throw exception`() {
        assertDoesNotThrow {
            ConsoleNotificationSender().sendNotification("one two three four")
        }
    }
}
