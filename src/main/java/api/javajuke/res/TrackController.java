package api.javajuke.res;

import api.javajuke.data.model.Track;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.TrackService;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class TrackController implements VersionController{

    private final TrackService trackService;

    @Value("${juke.scan.directory}")
    private String scanDirectory;

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
     * Creates an endpoint that returns a json response with all tracks. Returns the list as
     * an object with data as its key.
     *
     * @return all tracks as a json response
     */
    @GetMapping("/tracks")
    public ResponseEntity index(@RequestParam(value = "search", required = false) Optional<String> search) {
        List<Track> tracks;
        if(search.isPresent()){
            tracks = trackService.getTracks(search);
        } else {
            tracks = trackService.getTracks();
        }

        HashMap<String, List<Track>> map = new HashMap<>();
        map.put("data", tracks);

        return new ResponseEntity<>(map, HttpStatus.OK);
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
     * Creates an endpoint that creates a new track for each uploaded file.
     *
     * @param files mp3 files which contain track information
     * @return the newly created track as a json response
     */
    @PostMapping(value = "/tracks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity create(@RequestParam("files") MultipartFile files[]) {
        List<Track> tracks = trackService.createTracks(files);

        HashMap<String, List<Track>> map = new HashMap<>();
        map.put("data", tracks);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    /**
     * Creates an endpoint that deletes a track with the specified id.
     *
     * @param id the id of the track to delete
     * @throws FileNotFoundException when the track file is not found
     */
    @DeleteMapping(value = "/tracks/{id}")
    public void delete(@PathVariable("id") long id) throws FileNotFoundException {
        trackService.deleteTrack(id);
    }

    @GetMapping(value = "/tracks/sync")
    public ResponseEntity sync() {
        File directory = new File(scanDirectory);
        File[] files = directory.listFiles();

        List<Track> tracks = trackService.createTracksFromSync(files);

        HashMap<String, List<Track>> map = new HashMap<>();
        map.put("data", tracks);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
