package people.data.cloud.example.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
class ExampleConsumerApplication

fun main(args: Array<String>) {
    runApplication<ExampleConsumerApplication>(*args)
}
