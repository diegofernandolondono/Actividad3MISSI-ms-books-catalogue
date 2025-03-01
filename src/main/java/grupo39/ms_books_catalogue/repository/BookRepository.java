package grupo39.ms_books_catalogue.repository;

import grupo39.ms_books_catalogue.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar libros por título (sin distinguir mayúsculas o minúsculas)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Buscar libros por autor (sin distinguir mayúsculas o minúsculas)
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Buscar libros por categoría (sin distinguir mayúsculas o minúsculas)
    List<Book> findByCategoryContainingIgnoreCase(String category);

    // Buscar un libro por ISBN
    Optional<Book> findByIsbn(String isbn);

    // Buscar libros por visibilidad
    List<Book> findByVisible(boolean visible);

    // Buscar libros por título, autor y categoría (sin distinguir mayúsculas o minúsculas)
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndCategoryContainingIgnoreCase(String title, String author, String category);

    // Verificar si un libro tiene stock disponible
    Optional<Book> findByIdAndStockGreaterThan(Long id, int stock);

}
