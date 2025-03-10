package br.com.sgs.repository;

import br.com.sgs.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository <Book, Long> {   }
