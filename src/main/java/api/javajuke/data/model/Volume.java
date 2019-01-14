package api.javajuke.data.model;

import lombok.Data;

@Data
public class Volume {
    private int volume;

    public Volume(int volume) {
        this.volume = volume;
    }
}
