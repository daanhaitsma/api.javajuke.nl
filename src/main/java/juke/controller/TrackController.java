package juke.controller;

import juke.entity.Track;
import juke.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class TrackController {

    private final TrackRepository trackRepository;
    private final HttpServletRequest request;
    @Value("${juke.tracks.directory}")
    private String uploadDirectory;

    @Autowired
    public TrackController(TrackRepository trackRepository, HttpServletRequest request) {
        this.trackRepository = trackRepository;
        this.request = request;
    }

    @GetMapping("/api/tracks")
    public List<Track> index(){
        return trackRepository.findAll();
    }

    @GetMapping("/api/tracks/{id}")
    public Track show(@PathVariable String id){
        long trackId = Long.parseLong(id);
        return trackRepository.findOne(trackId);
    }

    @RequestMapping(value = "/api/tracks/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Track add(@RequestParam("file") MultipartFile file) throws IOException {
        if(!new File(uploadDirectory).exists())
        {
            new File(uploadDirectory).mkdir();
        }

        String fileName = file.getOriginalFilename();
        String filePath = uploadDirectory + fileName;

        File destination = new File(filePath);
        file.transferTo(destination);

        Track track = new Track(filePath);

        return trackRepository.save(track);
    }
}
