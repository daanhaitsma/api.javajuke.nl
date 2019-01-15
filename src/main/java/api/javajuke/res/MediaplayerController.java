package api.javajuke.res;

import api.javajuke.data.model.PlayerState;
import api.javajuke.data.model.Track;
import api.javajuke.service.MediaplayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@CrossOrigin
@RestController
public class MediaplayerController {
    private final MediaplayerService mediaplayerService;

    @Autowired
    public MediaplayerController(MediaplayerService mediaplayerService){
        this.mediaplayerService = mediaplayerService;
    }

    @GetMapping("/playerstate")
    public PlayerState getPlayerState(){
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/toggleplay")
    public PlayerState playMusic(){
        mediaplayerService.playMusic();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/stopmusic")
    public PlayerState stopMusic(){
        mediaplayerService.stopMusic();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/nextsong")
    public PlayerState nextSong(){
        mediaplayerService.nextSong();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/previoussong")
    public PlayerState previousSong(){
        mediaplayerService.previousSong();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/shuffle")
    public PlayerState toggleShuffle(){
        mediaplayerService.toggleShuffle();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/repeat")
    public PlayerState repeat(){
        mediaplayerService.setRepeat();
        return mediaplayerService.getPlayerState();
    }

    @PutMapping("/volume")
    public PlayerState setVolume(@RequestBody MultiValueMap<String, String> body){
        String volume = body.getFirst("volume");
        mediaplayerService.setVolume(Integer.valueOf(volume));
        return mediaplayerService.getPlayerState();
    }

    @GetMapping("/addsong")
    public void addSong(){
        mediaplayerService.addSong();
    }

    @GetMapping("/current")
    public Track getCurrentTrack(){
        return mediaplayerService.getCurrentTrack();
    }

}