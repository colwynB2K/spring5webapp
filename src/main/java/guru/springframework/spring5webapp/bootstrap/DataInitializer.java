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

        Author tolkien = new Author("JRR", "Tolkien");
        Book lotr = new Book("The Fellowship Of The Ring", "123456789");
        tolkien.addBook(lotr);

        authorRepository.save(tolkien);
        // bookRepository.save(lotr); Expecting the CASCADE to also save the

        Author martin = new Author("George R.R.", "Martin");
        Book got = new Book ("A Game Of Thrones", "69696666969");
        martin.addBook(got);

        authorRepository.save(martin);
        //bookRepository.save(got);

        System.out.println("Started in DataInitializer");
        System.out.println("Books: " + bookRepository.count());

        Publisher publisher = new Publisher("HBO", "6th Avenue 100-1108", "10036", "New York");
        publisherRepository.save(publisher);

        System.out.println("Publishers: " + publisherRepository.findAll());
    }
}
