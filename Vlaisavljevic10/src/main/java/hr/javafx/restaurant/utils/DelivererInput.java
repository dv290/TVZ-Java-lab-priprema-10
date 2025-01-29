package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Deliverer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos i kreiranje objekata tipa {@link Deliverer}.
 * Ova klasa omogućava unos podataka o dostavljačima, provjeru jedinstvenosti unosa
 * te kreiranje objekata dostavljača koristeći Builder pattern.
 */

public class DelivererInput {
    private DelivererInput(){}

    public static void loadDeliverers(Set<Deliverer> deliverers, List<Contract> contracts, AtomicInteger contractIndex) {
        String filePath = "dat/deliverers.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ( line != null && !line.trim().isEmpty() ) {
                String id = line.trim();
                String firstName = br.readLine().trim();
                String lastName = br.readLine().trim();

                Long contractId = Long.parseLong(br.readLine().trim());
                Contract contract = null;
                if(contractIndex.get() < contracts.size()) {
                    contract = contracts.get(contractIndex.getAndIncrement());
                }

                if (contract == null) {
                    log.error("Nije pronađen ID: {}", contractId);
                    break;
                }

                Bonus bonus = new Bonus(new BigDecimal(br.readLine().trim()));

                Deliverer deliverer = new Deliverer.DelivererBuilder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setContract(contract)
                        .setBonus(bonus)
                        .build();

                deliverer.setId(Long.parseLong(id));

                deliverers.add(deliverer);

                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke: ", e);
        }
    }

}
