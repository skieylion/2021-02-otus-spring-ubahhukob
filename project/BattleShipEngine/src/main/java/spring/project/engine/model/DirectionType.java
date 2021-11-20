package spring.project.engine.model;

public enum DirectionType {
    UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT"), ZERO("ZERO");
    private String title;

    DirectionType(String alignType) {
        title = alignType;
    }

    public int getX() {
        switch (title) {
            case "LEFT":
                return -1;
            case "RIGHT":
                return 1;
            default:
                return 0;
        }
    }

    public int getY() {
        switch (title) {
            case "UP":
                return -1;
            case "DOWN":
                return 1;
            default:
                return 0;
        }
    }

    public DirectionType getReverseDirection() {
        switch (title) {
            case "UP":
                return DOWN;
            case "DOWN":
                return UP;
            case "LEFT":
                return RIGHT;
            case "RIGHT":
                return LEFT;
            default:
                return ZERO;
        }
    }

}
