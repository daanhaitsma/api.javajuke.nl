package api.javajuke.service;

import api.javajuke.data.TrackRepository;
import api.javajuke.data.model.Track;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.mpatric.mp3agic.*;

@Service
public class TrackService {

    @Value("${juke.tracks.directory}")
    private String uploadDirectory;

    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getTracks() {
        return trackRepository.findAll();
    }

    public Track getTrack(long id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Track with ID " + id + " not found." ));
    }

    public Track createTrack(MultipartFile file) throws IOException, InvalidDataException, UnsupportedTagException {
        if(!new File(uploadDirectory).exists())
        {
            new File(uploadDirectory).mkdir();
        }

        String fileName = file.getOriginalFilename();
        String filePath = uploadDirectory + fileName;

        File destination = new File(filePath);
        file.transferTo(destination);

        Track track = new Track(filePath);

        Mp3File mp3File = new Mp3File(destination);
        track.setDuration(mp3File.getLengthInSeconds());
        if(mp3File.hasId3v1Tag()){
            ID3v1 id3v1Tag = mp3File.getId3v1Tag();
            track.setArtist(id3v1Tag.getArtist());
            track.setTitle(id3v1Tag.getTitle());
            track.setAlbum(id3v1Tag.getAlbum());
        }

        return trackRepository.save(track);
    }

    public void deleteTrack(long id) throws FileNotFoundException {
        Track track = getTrack(id);

        String filePath = track.getPath();

        File file = new File(filePath);

        if (!file.delete()) {
            throw new FileNotFoundException();
        }

        trackRepository.delete(track);
    }
}
