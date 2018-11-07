package eu.profinit.education.flightlog.domain.repositories;

import eu.profinit.education.flightlog.domain.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // TODO: Vytvořte metodu podle jejíhož názvu SpringData správně načte lety, které jsou právě ve vzduchu
    // Lety by se měly řadit od nejstarších a v případě shody podle ID tak, aby vlečná byla před kluzákem, který táhne
    // Výsledek si můžete ověřit v testu k této tříde v modulu services


    // TODO: Vytvorte metodu pro nacteni vlecnych letu pro vytvoreni dvojice letu na obrazovce Report

}

