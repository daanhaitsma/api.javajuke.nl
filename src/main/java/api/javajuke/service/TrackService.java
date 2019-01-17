package api.javajuke.service;

import api.javajuke.data.AlbumRepository;
import api.javajuke.data.TrackRepository;
import api.javajuke.data.model.Album;
import api.javajuke.data.model.Track;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mpatric.mp3agic.*;

import javax.imageio.ImageIO;

@Service
public class TrackService {

    @Value("${juke.tracks.directory}")
    private String uploadDirectory;

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;

    /**
     * Constructor for the TrackService class.
     *
     * @param trackRepository the repository which contains all track data
     */
    public TrackService(TrackRepository trackRepository, AlbumRepository albumRepository) {
        this.trackRepository = trackRepository;
        this.albumRepository = albumRepository;
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
     * Returns a List object containing all tracks that are stored in the database and filters
     * out tracks based on the specified search query.     *
     *
     * @param search the string to filter the list of tracks
     * @return a list with all tracks
     */
    public List<Track> getTracks(Optional<String> search){
        String searchInput = search.get();
        return trackRepository.findAllByArtistContainingOrTitleContaining(searchInput, searchInput);
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
    public Track createTrack(File file) throws IOException, InvalidDataException, UnsupportedTagException {
        if(!new File(uploadDirectory).exists())
        {
            // Create upload directory if it doesn't exist
            new File(uploadDirectory).mkdir();
        }

        String fileName = file.getName();
        String filePath = uploadDirectory + fileName;

        File destination = new File(filePath);

        Track track = new Track(filePath);
        file.renameTo(destination);

        Mp3File mp3File = new Mp3File(destination);
        track.setDuration(mp3File.getLengthInSeconds());
        if(mp3File.hasId3v2Tag()){
            // Check meta data
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            String artist = id3v2Tag.getArtist();
            String title = id3v2Tag.getTitle();
            String album = id3v2Tag.getAlbum();

            byte[] imageData = id3v2Tag.getAlbumImage();

            if(artist == null || artist.isEmpty()) {
                destination.delete();
                throw new IllegalArgumentException("Artist field is missing");
            }

            if(title == null || title.isEmpty()) {
                destination.delete();
                throw new IllegalArgumentException("Title field is missing");
            }

            if(trackRepository.findByTitleAndArtist(title, artist).isPresent()) {
                destination.delete();
                throw new BadRequestException("Song already in library");
            }

            Album albumObject = null;
            if (imageData != null) {

                if(!new File(uploadDirectory + "/albumcover").exists())
                {
                    // Create upload directory if it doesn't exist
                    new File(uploadDirectory + "/albumcover").mkdir();
                }

                String imageMimeType = id3v2Tag.getAlbumImageMimeType();
                String imageExtension;

                switch (imageMimeType) {
                    case "image/png":
                        imageExtension = ".png";
                        break;
                    default:
                        imageExtension = ".jpg";
                        break;
                }

                File albumFolder = new File(uploadDirectory + "/albumcover");
                File listOfAlbumCovers[] = albumFolder.listFiles();

                String albumCoverImageName = artist + album + imageExtension;
                // If file exists find the matching database entry
                if(albumRepository.findByCoverPath(albumCoverImageName).isPresent()) {
                    albumObject = albumRepository.findByCoverPath(albumCoverImageName)
                            .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));
                } else {
                    // No cover image found, create a new album cover
                    albumObject = new Album(album, albumCoverImageName);
                    albumRepository.save(albumObject);
                }

                // Look in the folder for the album cover image
                boolean albumCoverImageFound = false;
                for (File fileLoop : listOfAlbumCovers) {
                    if (fileLoop.isFile() && fileLoop.getName().equals(albumCoverImageName)) {
                        System.out.println(fileLoop.getName() + " already exists, not adding this image");
                        albumCoverImageFound = true;
                    }
                }

                // If no album cover image is found, move the file to the /albumcover folder
                if(!albumCoverImageFound) {
                    String albumCoverPath = uploadDirectory + "/albumcover/" + albumCoverImageName;

                    RandomAccessFile albumCover = new RandomAccessFile(albumCoverPath, "rw");
                    albumCover.write(imageData);
                    albumCover.close();
                }
            }

            track.setArtist(artist);
            track.setTitle(title);
            track.setAlbum(albumObject);
        }

        return trackRepository.save(track);
    }

    /**
     * Creates an array with newly created tracks.
     *
     * @param files the uploaded files
     * @return the list with newly created tracks
     */
    public List<Track> createTracks(MultipartFile[] files) {
        List<Track> tracks = new ArrayList<>();

        for(MultipartFile uploadedFile : files) {
            try {
                File file = convertMultipartFileToFile(uploadedFile);
                Track track = createTrack(file);

                tracks.add(track);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        return tracks;
    }

    /**
     * Creates an array with newly created tracks from the sync directory.
     *
     * @param files the files from the sync directory
     * @return the list with newly created tracks
     */
    public List<Track> createTracksFromSync(File[] files) {
        List<Track> tracks = new ArrayList<>();

        for(File file : files) {
            try {
                Track track = createTrack(file);

                tracks.add(track);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        return tracks;
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
            throw new FileNotFoundException("File could not be deleted");
        }

        Long albumId = track.getAlbum().getId();

        trackRepository.delete(track);

        // If all tracks of a certain album are deleted, remove them from the album table as well.
        if(trackRepository.findByAlbum_Id(albumId).size() == 0) {
            System.out.println("ALBUM ID IS: " + albumId);
            Album album = albumRepository.findById(albumId)
                    .orElseThrow(() -> new EntityNotFoundException("Something went wrong, please try again later."));
            String albumPath = uploadDirectory + "albumcover/" + album.getCoverPath();

            // Delete the album cover image
            File destination = new File(albumPath);
            destination.delete();

            albumRepository.deleteById(albumId);
        }

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = uploadDirectory + fileName;

        File destination = new File(filePath);

        multipartFile.transferTo(destination);

        return destination;
    }
}
