package api.stenden.res;

import api.stenden.data.TrackRepository;
import api.stenden.data.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrackController {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackController(TrackRepository trackRepository)
    {
        this.trackRepository = trackRepository;
    }

    @GetMapping("/test")
    public String test() {
        return "Test route";
    }

    @GetMapping("/tracks")
    public List<Track> index() {
        return trackRepository.findAll();
    }
}
