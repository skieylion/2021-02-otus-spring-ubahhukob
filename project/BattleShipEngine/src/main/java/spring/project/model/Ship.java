package spring.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ship {
    private int size;
    private Point point;
    private AlignType align;
}