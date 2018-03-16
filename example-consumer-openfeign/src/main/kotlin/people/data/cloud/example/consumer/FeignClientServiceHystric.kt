package people.data.cloud.consumer

import org.springframework.stereotype.Component


@Component
class FeignClientServiceHystric : FeignClientService {
    override fun aAndb(a: Int, b: Int): String {
        return "sorry can't calculate  $a + $b"
    }
}