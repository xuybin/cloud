package people.data.cloud.example.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.web.client.RestTemplate
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean



@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
class ExampleConsumerApplication

fun main(args: Array<String>) {
    runApplication<ExampleConsumerApplication>(*args)
}

@Bean
@LoadBalanced
fun restTemplate(): RestTemplate {
    return RestTemplate()
}
