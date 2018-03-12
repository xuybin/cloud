package people.data.cloud.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider")
public interface SchedualServiceHi {
    @RequestMapping(value = "/{a}/{b}",method = RequestMethod.GET)
    String aAndb(@PathVariable int a,@PathVariable int b);
}
