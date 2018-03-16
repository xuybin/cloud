package people.data.cloud.consumer

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.beans.factory.annotation.Autowired



@Service
class FeignClientService {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @HystrixCommand(fallbackMethod = "aAndbError")
    fun aAndb(a: Int,b: Int): String?{
        return restTemplate.getForObject("http://example-publisher/$a/$b", String::class.java)
    }

    fun aAndbError(name: String): String {
        return "hi,$name,sorry,error!"
    }
}
