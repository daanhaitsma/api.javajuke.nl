package api.javajuke.res;

import api.javajuke.data.model.Position;
import api.javajuke.data.model.Volume;
import api.javajuke.exception.BadRequestException;
import api.javajuke.exception.EntityNotFoundException;
import api.javajuke.service.MediaplayerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @PutMapping("/toggleplay")
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

    @GetMapping("/shuffle")
    public void getShuffle(){
        mediaplayerService.getShuffle();
    }

    @PutMapping("/shuffle")
    public void toggleShuffle(){
        mediaplayerService.toggleShuffle();
    }

    @GetMapping("/repeat")
    public void repeat(){
        mediaplayerService.setRepeat();
    }

    @PutMapping("/volume")
    public void setVolume(@RequestBody MultiValueMap<String, String> body){
        String volume = body.getFirst("volume");
        mediaplayerService.setVolume(Integer.valueOf(volume));
    }

    @GetMapping("/volume")
    public Volume getVolume(){
        return new Volume(mediaplayerService.getVolume());
    }

    @GetMapping("/position")
    public Position getPosition(){
        if (mediaplayerService.isPlaying() || mediaplayerService.isPaused()) {
            return new Position(mediaplayerService.getPosition());
        } else {
            return new Position(0);
        }
    }
    
    @GetMapping("/addsong")
    public void addSong(){
        mediaplayerService.addSong();
    }

}