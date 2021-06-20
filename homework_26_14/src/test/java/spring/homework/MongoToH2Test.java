package spring.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.homework.h2.repositories.BookRepositoryH2;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@SpringBatchTest
public class MongoToH2Test {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private BookRepositoryH2 bookRepositoryH2;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {

        Assertions.assertEquals(bookRepositoryH2.findAll().size(),0);

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo("importBookJob");
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertTrue(bookRepositoryH2.findAll().size()>0);
    }
}
