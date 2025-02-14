package br.com.sgs.repository;

import br.com.sgs.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CambioRepository extends JpaRepository <Cambio, Long> {

    Cambio findByFromAndTo(String from, String to);
}
