package api.javajuke.res;

import api.javajuke.data.model.PlayerState;
import api.javajuke.service.MediaplayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/playmusic")
    public void playMusic(){
        mediaplayerService.playMusic();
    }

    @PutMapping("/stopmusic")
    public void stopMusic(){
        mediaplayerService.stopMusic();
    }

    @PutMapping("/nextsong")
    public void nextSong(){
        mediaplayerService.nextSong();
    }

    @PutMapping("/previoussong")
    public void previousSong(){
        mediaplayerService.previousSong();
    }

    @PutMapping("/shuffle")
    public void toggleShuffle(){
        mediaplayerService.toggleShuffle();
    }

    @PutMapping("/repeat")
    public void repeat(){
        mediaplayerService.setRepeat();
    }

    @PutMapping("/volume")
    public void setVolume(@RequestBody MultiValueMap<String, String> body){
        String volume = body.getFirst("volume");
        mediaplayerService.setVolume(Integer.valueOf(volume));
    }
    
    @GetMapping("/addsong")
    public void addSong(){
        mediaplayerService.addSong();
    }

}