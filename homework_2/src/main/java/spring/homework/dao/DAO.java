package spring.homework.dao;

import java.io.IOException;
import java.util.List;

public interface DAO<Entity> {
    public List<Entity> findAll() throws IOException;
}
