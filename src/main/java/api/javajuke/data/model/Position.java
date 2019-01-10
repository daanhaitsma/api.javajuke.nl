package api.javajuke.data.model;

import lombok.Data;

@Data
public class Position {
    private int position;

    public Position(int position) {
        this.position = position;
    }
}
