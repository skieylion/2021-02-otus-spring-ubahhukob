package spring.homework.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@EnableMongock
public abstract class AbstractTest {
}
