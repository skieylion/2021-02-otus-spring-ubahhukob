package spring.project.model;

public enum AlignType {
    UP("UP"),DOWN("DOWN"),LEFT("LEFT"),RIGHT("RIGHT");
    private String title;
    AlignType(String alignType){
        title=alignType;
    }
    public int getX(){
        switch (title){
            case "LEFT":
                return -1;
            case "RIGHT":
                return 1;
            default: return 0;
        }
    }
    public int getY(){
        switch (title){
            case "UP":
                return -1;
            case "DOWN":
                return 1;
            default: return 0;
        }
    }

}
