package people.data.cloud.provider

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class AddController {

    @Value("\${server.port}")
    lateinit var port: String

    @RequestMapping(value = "/{a}/{b}", method = arrayOf(RequestMethod.GET))
    fun add(@PathVariable a: Int, @PathVariable b: Int): Mono<String> {
        //return "from "+port+":"+a+"+"+b+"="+(a+b);
        return Mono.create { cityMonoSink -> cityMonoSink.success("from " + port + ":" + a + "+" + b + "=" + (a + b)) }
    }
}
