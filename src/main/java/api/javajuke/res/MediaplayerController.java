package api.javajuke.res;

import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.MediaplayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class MediaplayerController {
    private final MediaplayerService mediaplayerService;

    @Autowired
    public MediaplayerController(MediaplayerService mediaplayerService){
        this.mediaplayerService = mediaplayerService;
    }

    @GetMapping("/playmusic")
    public void playMusic(){
        mediaplayerService.playMusic();;
    }

    @GetMapping("/pausemusic")
    public void pauseMusic(){
        mediaplayerService.pauseMusic();
    }

    @GetMapping("/stopmusic")
    public void stopMusic(){
        mediaplayerService.stopMusic();
    }

    @GetMapping("/nextsong")
    public void nextSong(){
        mediaplayerService.nextSong();
    }

    @GetMapping("/previoussong")
    public void previousSong(){
        mediaplayerService.previousSong();
    }

    @GetMapping("/shuffle")
    public void shuffle(){
        mediaplayerService.shuffle();
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