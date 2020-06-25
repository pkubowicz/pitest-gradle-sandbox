package com.example.data

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication(scanBasePackages = ["com.example.data"])
@EnableReactiveMongoRepositories(
    basePackages = [
        "com.example.data"
    ]
)
class DataConfiguration {
}
