package api.javajuke.res;

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

    /**
     * Constructor for the TrackController class.
     *
     * @param trackService the track service containing all track logic
     */
    @Autowired
    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }

    /**
     * Creates an endpoint that returns a json response with all tracks.
     *
     * @return all tracks as a json response
     */
    @GetMapping("/tracks")
    public List<Track> index() {
        return trackService.getTracks();
    }

    /**
     * Creates an endpoint that shows a track with the specified id.
     *
     * @param id the id of the track
     * @return the track as a json response
     * @throws EntityNotFoundException when the track is not found
     */
    @GetMapping("/tracks/{id}")
    public Track index(@PathVariable("id") long id) throws EntityNotFoundException {
        return trackService.getTrack(id);
    }

    /**
     * Creates an endpoint that creates a new track.
     *
     * @param file mp3 file which contains track information
     * @return the newly created track as a json response
     * @throws IOException when the file upload does not work
     * @throws InvalidDataException when the file is not a mp3 file
     * @throws UnsupportedTagException when the mp3 file does not have the correct tags
     */
    @PostMapping(value = "/tracks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Track create(@RequestParam("file") MultipartFile file) throws IOException, InvalidDataException, UnsupportedTagException {
        return trackService.createTrack(file);
    }

    /**
     * Creates an endpoint that deletes a track with the specified id.
     *
     * @param id the id of the track to delete
     * @throws FileNotFoundException when the track file is not found
     */
    @DeleteMapping(value = "/tracks/{id}")
    public void create(@PathVariable("id") long id) throws FileNotFoundException {
        trackService.deleteTrack(id);
    }
}
