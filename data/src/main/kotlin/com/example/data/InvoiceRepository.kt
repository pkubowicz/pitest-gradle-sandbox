package com.example.data

import com.example.core.Invoice
import com.example.core.InvoiceStatus
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface InvoiceRepository : ReactiveMongoRepository<Invoice, String> {

    fun findAllByStatusNotIn(status: List<InvoiceStatus>): Flux<Invoice>
}
