package guru.springframework.spring5webapp.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // This is referred to as 'leakage' as our persistence layer mapping of properties to database columns is leaking up into our pure POJO object model

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(
            mappedBy = "authors",
     //       cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    // Add convenience method for bidirectional relationship
    public void addBook(Book book) {
        // Create the bi-directional link
        book.addAuthor(this);	// Make sure the link from the book back to the author is also set in the book Java object
        books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                                     // if same instance, same memory address
        if (o == null || getClass() != o.getClass()) return false;      // if one is null or different class

        Author author = (Author) o;

        return id != null ? id.equals(author.id) : author.id == null;   // if author id isn't null, compare it to id of incoming author object, otherwise just check that incoming author also has id null because null.equals(something) doesn't work out at runtime!
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", books=" + books +
                '}';
    }
}
