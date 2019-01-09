package api.javajuke.res;

import api.javajuke.service.MediaplayerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/setvolume/{volume}")
    public void setVolume(@PathVariable("volume") int volume){
        mediaplayerService.setVolume(volume);
    }
}