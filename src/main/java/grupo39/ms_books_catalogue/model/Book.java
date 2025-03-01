package grupo39.ms_books_catalogue.model;

import jakarta.persistence.*;
import lombok.Data;  // Lombok para generar getters, setters, toString, equals, hashCode, etc.
import java.util.Date;
import java.math.BigDecimal; // Importar para manejar decimales

@Entity
@Table(name = "books")
@Data // Lombok genera automáticamente los métodos getter, setter, toString(), equals(), hashCode() y el constructor por defecto.
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private String category;
    private Double rating; // De 1 a 5
    private int stock; // Cantidad en stock
    private BigDecimal price; // Precio del libro
    private boolean visible; // Visibilidad del libro
    private Date publicationDate; // Fecha de publicación
    private String description;
}
