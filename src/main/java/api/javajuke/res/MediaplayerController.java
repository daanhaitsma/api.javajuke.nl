package api.javajuke.res;

import api.javajuke.data.model.PlayerState;
import api.javajuke.service.MediaplayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MediaplayerController implements VersionController {
    private final MediaplayerService mediaplayerService;

    @Autowired
    public MediaplayerController(MediaplayerService mediaplayerService) {
        this.mediaplayerService = mediaplayerService;
    }

    @GetMapping("/player/state")
    public PlayerState getPlayerState() {
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/toggleplay")
    public PlayerState playMusic() {
        mediaplayerService.togglePlay();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/stop")
    public PlayerState stopMusic() {
        mediaplayerService.stopMusic();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/next")
    public PlayerState nextSong() {
        mediaplayerService.nextSong();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/previous")
    public PlayerState previousSong() {
        mediaplayerService.previousSong();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/toggleshuffle")
    public PlayerState toggleShuffle() {
        mediaplayerService.toggleShuffle();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/togglerepeat")
    public PlayerState toggleRepeat() {
        mediaplayerService.toggleRepeat();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/volume")
    public PlayerState setVolume(@RequestBody MultiValueMap<String, String> body){
        String volume = body.getFirst("volume");
        mediaplayerService.setVolume(Integer.valueOf(volume));
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/player/queue")
    public PlayerState addTrackToQueue(@RequestBody MultiValueMap<String, String> body){
        Long trackID = Long.parseLong(body.getFirst("track"));

        mediaplayerService.addTrackToQueue(trackID);
        return mediaplayerService.getPlayerState();
    }

}