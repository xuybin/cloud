package people.data.cloud.consumer

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "provider1")
interface FeignClientService {
    @RequestMapping(value = "/{a}/{b}", method = arrayOf(RequestMethod.GET))
    fun aAndb(@PathVariable a: Int, @PathVariable b: Int): String
}
