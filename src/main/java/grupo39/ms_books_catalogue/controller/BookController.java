package grupo39.ms_books_catalogue.controller;

import grupo39.ms_books_catalogue.model.Book;
import grupo39.ms_books_catalogue.repository.BookRepository;
import grupo39.ms_books_catalogue.service.BookService;
import lombok.RequiredArgsConstructor;  // Lombok para generar el constructor con todos los parámetros finales.
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal; // Importar para manejar decimales

@RestController
@RequestMapping("/api/books")
//@CrossOrigin(origins = "http://localhost:8081") // URL de ms-books-payments
@RequiredArgsConstructor // Lombok genera el constructor con todos los parámetros necesarios
public class BookController {

    private final BookService bookService; // Inyección de dependencia automática
    private final BookRepository bookRepository;  // Inyección del repositorio

    // Obtener todos los libros
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    // Obtener un libro por su ID
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // Crear un nuevo libro
    //@PostMapping
    //public Book createBook(@RequestBody Book book) {
    //    return bookService.createBook(book);
    //}

    // Crear un nuevo libro con precio
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        // Validar si el precio es nulo y asignar un valor predeterminado
        if (book.getPrice() == null) {
            book.setPrice(BigDecimal.ZERO);
        }
        return bookService.createBook(book);
    }

    // Actualizar un libro
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    // Eliminar un libro
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // Buscar libros por título
    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    // Buscar libros por autor
    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    // Buscar libros por categoría
    @GetMapping("/search/category")
    public List<Book> searchByCategory(@RequestParam String category) {
        return bookService.searchByCategory(category);
    }

    // Buscar libros por visibilidad
    @GetMapping("/search/visibility")
    public List<Book> searchByVisibility(@RequestParam boolean visible) {
        return bookService.searchByVisibility(visible);
    }

    // Buscar libros por combinación de atributos
    @GetMapping("/search")
    public List<Book> searchByTitleAuthorCategory(@RequestParam String title, @RequestParam String author, @RequestParam String category) {
        return bookService.searchByTitleAuthorCategory(title, author, category);
    }

    //Actualizar el Stock del Libre al realizar la compra DFL
    @PutMapping("/updateStock/{id}/{stock}")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @PathVariable int stock) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setStock(stock);
            bookRepository.save(book);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Verificar si un libro tiene stock disponible
    @GetMapping("/{id}/stock")
    public String checkBookStock(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getBookById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (bookService.hasStock(id)) {
                return "Hay " + book.getStock() + " unidades disponibles del libro: " + book.getTitle();
            } else {
                return "No hay stock disponible para el libro: " + book.getTitle() + " con ID: " + id + ".";
            }
        } else {
            return "El libro con ID: " + id + " no existe.";
        }

    }
}
