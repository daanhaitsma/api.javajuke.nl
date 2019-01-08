package api.javajuke.service;

import jaco.mp3.player.MP3Player;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MediaplayerService {

 public static void main(String[] args){
     PlayerThread player_thread = new PlayerThread();
     player_thread.start();
 }
}

class PlayerThread extends Thread{
    MP3Player mp3Player;

    public void run(){
        try {
            mp3Player = new MP3Player(new File("resource/Cage The Elephant - Trouble.mp3"));
            mp3Player.play();
            while(!mp3Player.isStopped()){
                Thread.sleep(5000);
            }
        }catch(Exception e) {
            System.err.println(e);
        }
    }
}
