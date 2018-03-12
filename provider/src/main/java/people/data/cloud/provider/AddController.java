package people.data.cloud.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddController {

    @Value("${server.port}")
    String port;
    @RequestMapping(value = "/{a}/{b}",method = RequestMethod.GET)
    public String add(@PathVariable int a, @PathVariable int b){
        return "from "+port+":"+a+"+"+b+"="+(a+b);
    }
}
