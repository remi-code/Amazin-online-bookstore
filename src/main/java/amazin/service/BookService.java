package amazin.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import amazin.model.Book;
import amazin.model.Tag;
import amazin.model.User;
import amazin.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    public List<Book> getAll() {
        return (List<Book>) repository.findAll();
    }

    public List<Book> getAllBooksMatching(Tag tag) {
        return (List<Book>) repository.findDistinctByTags(tag);
    }

    public Set<Book> getAllRecommendedBooks(User user) {
        if (user == null) {
            return new HashSet<Book>();
        }
        Set<Tag> tags = user.getTags();
        List<Book> books = new ArrayList<>();

        for (Tag tag: tags) {
            books.addAll(getAllBooksMatching(tag));
        }

        return new HashSet<Book>(books);
    }

    public Book create(Book book) {
        repository.save(book);
        return book;
    }

    public Book update(Book updatedBook) {
        Optional<Book> bookToBeUpdated = repository.findById(updatedBook.getId());

        if (bookToBeUpdated.isPresent()) {
            Book book = bookToBeUpdated.get();
            book.setAuthor(updatedBook.getAuthor());
            book.setDescription(updatedBook.getDescription());
            book.setISBN(updatedBook.getISBN());
            book.setInventory(updatedBook.getInventory());
            book.setName(updatedBook.getName());
            book.setPicture_url(updatedBook.getPicture_url());
            book.setPrice(updatedBook.getPrice());
            book.setPublisher(updatedBook.getPublisher());
            book.setTags(updatedBook.getTags());
            repository.save(book);
            return book;
        }
        return null;
    }

    public Optional<Book> delete(Long id) {
        Optional<Book> deleted = findById(id);
        if (deleted.isPresent()) {
            repository.delete(deleted.get());
        }

        return deleted;
    }
}
