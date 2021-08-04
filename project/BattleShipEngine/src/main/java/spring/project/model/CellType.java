package spring.project.model;

public enum CellType {
    VOID(0) {
        public String toString(){
            return "⬜";
        }
    },
    FULL(1) {
        public String toString(){
            return "⬛";
        }
    },
    MISS(2) {
        public String toString(){
            return "•";
        }
    },
    DAMAGE(3) {
        public String toString(){
            return "✘";
        }
    },
    MARK(4){
        public String toString(){
            return "✔";
        }
    };

    private final int value;

    CellType(int value) {
        this.value=value;
    };
}