package api.javajuke.res;

import api.javajuke.data.annotation.ApiVersion;
import api.javajuke.data.model.Track;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.TrackService;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class TrackController implements VersionController{

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
    public Track create(@RequestParam("file") MultipartFile file) throws IOException, InvalidDataException, UnsupportedTagException {
        return trackService.createTrack(file);
    }

    @DeleteMapping(value = "/tracks/{id}")
    public void create(@PathVariable("id") long id) throws FileNotFoundException {
        trackService.deleteTrack(id);
    }
}
