package spring.homework.domain;

public enum Permission {
    READ("READ"),WRITE("WRITE");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String get() {
        return permission;
    }
}
