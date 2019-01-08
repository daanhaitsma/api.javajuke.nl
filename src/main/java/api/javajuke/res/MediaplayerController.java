package api.javajuke.res;

import api.javajuke.service.MediaplayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaplayerController {
    private final MediaplayerService mediaplayerService;

    @Autowired
    public MediaplayerController(MediaplayerService mediaplayerService){
        this.mediaplayerService = mediaplayerService;
    }

    @GetMapping("/playit")
    public MediaplayerService PlayMusic(){
        MediaplayerService player = new MediaplayerService();
        return player;
    }
}
