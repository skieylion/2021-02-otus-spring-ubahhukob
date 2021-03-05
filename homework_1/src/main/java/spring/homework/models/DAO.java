package spring.homework.models;

import java.io.IOException;
import java.util.List;

public interface DAO<Entity> {
    public List<Entity> findAll() throws IOException;
}
