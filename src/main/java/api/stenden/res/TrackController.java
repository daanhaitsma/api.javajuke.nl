package api.stenden.res;

import api.stenden.data.model.Track;
import api.stenden.exception.EntityNotFoundException;
import api.stenden.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TrackController {

    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }

    @GetMapping("/tracks")
    public List<Track> index() {
        return trackService.getTracks();
    }

    @GetMapping("/tracks/{id}")
    public Track index(@PathVariable("id") long id) throws EntityNotFoundException {
        return trackService.getTrack(id);
    }

    @PostMapping(value = "/tracks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Track create(@RequestParam("file") MultipartFile file) throws IOException {
        return trackService.createTrack(file);
    }

    @DeleteMapping(value = "/tracks/{id}")
    public void create(@PathVariable("id") long id) throws FileNotFoundException {
        trackService.deleteTrack(id);
    }
}
