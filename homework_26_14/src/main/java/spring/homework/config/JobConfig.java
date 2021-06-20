package spring.homework.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;
import spring.homework.h2.domain.AuthorH2;
import spring.homework.h2.domain.BookH2;
import spring.homework.h2.domain.GenreH2;
import spring.homework.services.HandlerAuthorService;
import spring.homework.services.HandlerBookService;
import spring.homework.services.HandlerGenreService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableMongock
public class JobConfig {

    private static final int CHUNK_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @PersistenceContext
    private EntityManager em;





    @Bean
    public Job importBookJob(Step transformBookStep,Step transformGenreStep,Step transformAuthorStep) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .flow(transformGenreStep)
                .next(transformAuthorStep)
                .next(transformBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    //BOOK---------------------------------
    @Bean
    public MongoItemReader<Book> reader(MongoTemplate template) throws Exception {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoItemReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public JpaItemWriter<BookH2> writer() {
        return new JpaItemWriterBuilder<BookH2>()
                .entityManagerFactory(em.getEntityManagerFactory())
                .build();
    }


    @Bean
    public Step transformBookStep(
            MongoItemReader<Book> reader,
            JpaItemWriter<BookH2> writer,
            @Qualifier("bookProcessor") ItemProcessor<Book, BookH2> bookProcessor) {
        return stepBuilderFactory.get("transformBookStep")
                .<Book, BookH2>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(bookProcessor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Book book) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    @Override
                    public void beforeProcess(Book book) {
                        logger.info("Начало обработки");
                    }

                    @Override
                    public void afterProcess(Book book, BookH2 bookH22) {
                        logger.info("Конец обработки");
                    }

                    @Override
                    public void onProcessError(Book book, Exception e) {
                        logger.info("В процессе обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean("bookProcessor")
    public ItemProcessor<Book, BookH2> bookProcessor(HandlerBookService handlerBookService) {
        return handlerBookService::handle;
    }


    //GENRE-------------------------------------
    @Bean
    public MongoItemReader<Genre> readerGenre(MongoTemplate template) throws Exception {
        return new MongoItemReaderBuilder<Genre>()
                .name("mongoItemReaderGenre")
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public JpaItemWriter<GenreH2> writerGenre() {
        return new JpaItemWriterBuilder<GenreH2>()
                .entityManagerFactory(em.getEntityManagerFactory())
                .build();
    }

    @Bean("genreProcessor")
    public ItemProcessor<Genre, GenreH2> genreProcessor(HandlerGenreService handlerGenreService) {
        return handlerGenreService::handle;
    }

    @Bean
    public Step transformGenreStep(
            MongoItemReader<Genre> readerGenre,
            JpaItemWriter<GenreH2> writerGenre,
            @Qualifier("genreProcessor") ItemProcessor<Genre,GenreH2> genreProcessor) {
        return stepBuilderFactory.get("transformGenreStep")
                .<Genre, GenreH2>chunk(CHUNK_SIZE)
                .reader(readerGenre)
                .processor(genreProcessor)
                .writer(writerGenre)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    //AUTHOR------------------------
    @Bean
    public MongoItemReader<Author> readerAuthor(MongoTemplate template) throws Exception {
        return new MongoItemReaderBuilder<Author>()
                .name("mongoItemReaderAuthor")
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public JpaItemWriter<AuthorH2> writerAuthor() {
        return new JpaItemWriterBuilder<AuthorH2>()
                .entityManagerFactory(em.getEntityManagerFactory())
                .build();
    }

    @Bean("authorProcessor")
    public ItemProcessor<Author, AuthorH2> authorProcessor(HandlerAuthorService handlerAuthorService) {
        return handlerAuthorService::handle;
    }

    @Bean
    public Step transformAuthorStep(
            MongoItemReader<Author> readerAuthor,
            JpaItemWriter<AuthorH2> writerAuthor,
            @Qualifier("authorProcessor") ItemProcessor<Author,AuthorH2> authorProcessor) {
        return stepBuilderFactory.get("transformAuthorStep")
                .<Author, AuthorH2>chunk(CHUNK_SIZE)
                .reader(readerAuthor)
                .processor(authorProcessor)
                .writer(writerAuthor)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }


}
