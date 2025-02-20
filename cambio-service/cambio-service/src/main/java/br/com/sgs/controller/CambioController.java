package br.com.sgs.controller;

import br.com.sgs.dto.CambioResponse;
import br.com.sgs.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository cambioRepository;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public CambioResponse getCambio(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {

        var cambio = cambioRepository.findByFromAndTo(from, to);

        if (cambio == null) throw new RuntimeException("Currency Unsupported");

        var port = environment.getProperty("local.server.port");
        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal conversionValue = conversionFactor.multiply(amount);

        cambio.setConvertedValue(conversionValue.setScale(2, RoundingMode.CEILING));
        cambio.setEnvironment(port);

        return new CambioResponse(
                cambio.getId(),
                cambio.getFrom(),
                cambio.getTo(),
                cambio.getConversionFactor(),
                conversionValue,
                port
        );
    }
}
