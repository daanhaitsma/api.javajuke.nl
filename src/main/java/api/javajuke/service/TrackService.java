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

    /**
     * Constructor for the TrackService class.
     *
     * @param trackRepository the repository which contains all track data
     */
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Returns a List object containing all tracks
     * that are stored in the database.

     * @return a list with all tracks
     */
    public List<Track> getTracks() {
        return trackRepository.findAll();
    }

    /**
     * Returns a Track object which is found by searching the
     * database with the specified id.
     *
     * @param id the id of the track to find
     * @return   the track for the specified id
     */
    public Track getTrack(long id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Track with ID " + id + " not found." ));
    }

    /**
     * Creates and returns a new track with the specified file
     * by adding the track to the database and uploading
     * the file to the specified upload directory
     *
     * @param file the file to upload
     * @return the created track
     * @throws IOException when something goes wrong when uploading the file
     * @throws InvalidDataException when the uploaded file is invalid
     * @throws UnsupportedTagException when the tags of the uploaded file are unsupported
     */
    public Track createTrack(MultipartFile file) throws IOException, InvalidDataException, UnsupportedTagException {
        if(!new File(uploadDirectory).exists())
        {
            new File(uploadDirectory).mkdir();
        }

        String fileName = file.getOriginalFilename();
        String filePath = uploadDirectory + fileName;

        File destination = new File(filePath);

        Track track = new Track(filePath);
        file.transferTo(destination);

        Mp3File mp3File = new Mp3File(destination);
        track.setDuration(mp3File.getLengthInSeconds());
        if(mp3File.hasId3v2Tag()){
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            String artist = id3v2Tag.getArtist();
            String title = id3v2Tag.getTitle();
            String album = id3v2Tag.getAlbum();

            if(artist == null || artist.isEmpty()) {
                throw new IllegalArgumentException("Artist field is missing");
            }

            if(title == null || title.isEmpty()) {
                throw new IllegalArgumentException("Title field is missing");
            }

            track.setArtist(artist);
            track.setTitle(title);
            track.setAlbum(album);
        }

        return trackRepository.save(track);
    }

    /**
     * Deletes a track which is connected to the specified id
     * and also deletes the file which is associated with the track.
     *
     * @param id the track id to delete
     * @throws FileNotFoundException when the file is not found on the specified path
     */
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
