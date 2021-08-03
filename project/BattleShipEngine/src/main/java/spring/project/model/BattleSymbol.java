package spring.project.model;

public enum BattleSymbol {
    MISS {
        public String toString(){
            return "•";
        }
    },
    DAMAGE {
        public String toString(){
            return "✘";
        }
    },
    VOID {
        public String toString(){
            return "0";
        }
    }
}