package juke.controller;

import juke.entity.Track;
import juke.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TrackController {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
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
}
