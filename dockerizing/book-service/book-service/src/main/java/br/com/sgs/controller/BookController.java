package br.com.sgs.controller;

import br.com.sgs.dto.BookDTO;
import br.com.sgs.proxy.CambioProxy;
import br.com.sgs.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CambioProxy cambioProxy;

    @Operation(summary = "Find a specific book by your ID and currency")
    @GetMapping(value = "/{id}/{currency}")
    @Transactional
    public BookDTO findbook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {

        var book = bookRepository.getById(id);

        if (book == null) throw new RuntimeException("Book not found");

        var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

        var port = environment.getProperty("local.server.port");

        book.setEnvironment("Book port:" + port + " Cambio port " + cambio.getEnvironment());
        var price = cambio.getConvertedValue();

        return new BookDTO(
                book.getId(),
                book.getAuthor(),
                book.getLaunchDate(),
                price,
                book.getTitle(),
                book.getCurrency(),
                book.getEnvironment()
        );
    }

//    @GetMapping(value = "/{id}/{currency}")
//    @Transactional
//    public Book findbook(
//            @PathVariable("id") Long id,
//            @PathVariable("currency") String currency
//    ) {
//
//        var book = bookRepository.getById(id);
//
//        if (book == null) throw new RuntimeException("Book not found");
//
//
//        HashMap<String, String> params = new HashMap<>();
//
//        params.put("amount", book.getPrice().toString());
//        params.put("from", "USD");
//        params.put("to", currency);
//
//        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
//
//        var cambio = response.getBody();
//
////        var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);
//
//        var port = environment.getProperty("local.server.port");
//
//        book.setEnvironment(port);
////        book.setEnvironment(port + " FEIGN");
//        book.setPrice(cambio.getConvertedValue());
//
//        return book;
//    }

}
