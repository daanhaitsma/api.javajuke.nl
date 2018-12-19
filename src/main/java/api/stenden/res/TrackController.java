package api.stenden.res;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    public TrackController()
    {

    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
