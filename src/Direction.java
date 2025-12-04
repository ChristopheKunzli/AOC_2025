public enum Direction {
    LEFT("L"),
    RIGHT("R"),
    UP("U"),
    DOWN("D");

    private final String code;

    Direction(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
