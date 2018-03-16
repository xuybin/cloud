package people.data.cloud.consumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

import java.util.Random


@RestController
class FeignClientController {

    private val random = Random()

    @Autowired
    lateinit var feignClientService: FeignClientService

    @RequestMapping(value = "/1and2", method = arrayOf(RequestMethod.GET))
    @Throws(InterruptedException::class)
    fun sayHi(): Mono<String> {
        Thread.sleep(random.nextInt(1000).toLong())
        //return   schedualServiceHi.aAndb(1,2);
        return Mono.create { cityMonoSink -> cityMonoSink.success(feignClientService.aAndb(1, 2)) }
    }
}
