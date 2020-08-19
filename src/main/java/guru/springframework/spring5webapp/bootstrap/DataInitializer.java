package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Publisher publisher = new Publisher("HBO", "6th Avenue 100-1108", "10036", "New York");
        publisherRepository.save(publisher);

        Author tolkien = new Author("JRR", "Tolkien");
        Book lotr = new Book("The Fellowship Of The Ring", "123456789");
        tolkien.getBooks().add(lotr);
        lotr.getAuthors().add(tolkien);

        lotr.setPublisher(publisher);
        publisher.getBooks().add(lotr);

        authorRepository.save(tolkien); // Likely that you have to save this first because in the ManyToMany you defined that joinColumn on the Book side and on the Author that Books is mapped via the authors property?
        // Otherwise: Caused by: org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: guru.springframework.spring5webapp.domain.Author; nested exception is java.lang.IllegalStateException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: guru.springframework.spring5webapp.domain.Author
        // This will also happen if you don't save the publisher right after creating it

        // Already trying to define cascades also causes problems apparently. If you call 'publisherRepository.save(publisher);' before you try to save the book (and cascade the persist to Author and Publisher too you get an error about a Detached Publisher object

        bookRepository.save(lotr);
        publisherRepository.save(publisher);


        Author martin = new Author("George R.R.", "Martin");
        Book got = new Book ("A Game Of Thrones", "69696666969");
        martin.getBooks().add(got);
        got.getAuthors().add(martin);

        got.setPublisher(publisher);
        publisher.getBooks().add(got);

        authorRepository.save(martin);
        bookRepository.save(got);
        publisherRepository.save(publisher);

        System.out.println("Started in DataInitializer");
        System.out.println("Books: " + bookRepository.count());

        System.out.println("Publishers: " + publisherRepository.findAll());
        System.out.println("Publisher Number of Books: " + publisher.getBooks().size());
    }
}
