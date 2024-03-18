package pl.pajwoj.ztp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pajwoj.ztp.services.BookService;

@RestController
@RequestMapping(path = "api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Add a new book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Book already exists / empty fields.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "Book added successfully.",
                    content = @Content
            )
    })
    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@Parameter(description = "Details of the new book") @RequestParam String isbn, String name, String author) {
        return bookService.add(isbn, name, author);
    }

    @Operation(summary = "Remove a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Book not found / empty fields.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "Book removed successfully.",
                    content = @Content
            )
    })
    @Transactional
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> register(@Parameter(description = "ISBN of the book to be removed") @RequestParam String isbn) {
        return bookService.delete(isbn);
    }

    @Operation(summary = "Get a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Book not found / empty fields.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "Book found successfully.",
                    content = @Content
            )
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> get(@Parameter(description = "ID of the book to be retrieved") @PathVariable Long id) {
        return bookService.get(id);
    }
}
