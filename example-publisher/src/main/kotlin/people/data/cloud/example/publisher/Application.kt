package people.data.cloud.example.publisher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class ExamplePublisherApplication

fun main(args: Array<String>) {
    runApplication<ExamplePublisherApplication>(*args)
}
