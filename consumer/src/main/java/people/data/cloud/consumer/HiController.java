package people.data.cloud.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
public class HiController {

    private final Random random = new Random();

    @Autowired
    SchedualServiceHi schedualServiceHi;
    @RequestMapping(value = "/1and2",method = RequestMethod.GET)
    public String sayHi() throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
      return   schedualServiceHi.aAndb(1,2);
    }
}
