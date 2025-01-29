package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.model.Contract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos podataka o kuharima. Ova klasa omogućava unos imena, prezimena, ugovora i bonusa za kuhara.
 * Također provodi provjeru dupliciranja imena i prezimena kuhara.
 */

public class ChefsInput {
    private ChefsInput(){}

    public static void loadChefs(Set<Chef> chefs, List<Contract> contracts, AtomicInteger contractIndex) {
        String filePath = "dat/chefs.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ( line != null && !line.trim().isEmpty() ) {
                String id = line.trim();
                String firstName = br.readLine().trim();
                String lastName = br.readLine().trim();
                Long contractId = Long.parseLong(br.readLine().trim());

                Contract contract = null;
                if (contractIndex.get() < contracts.size()) {
                    contract = contracts.get(contractIndex.getAndIncrement()); // Increment index after use
                }

                if (contract == null) {
                   log.error("Nije pronađen ID: {}", contractId);
                    break;
                }

                Bonus bonus = new Bonus(new BigDecimal(br.readLine()));

                Chef chef = new Chef.ChefBuilder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setContract(contract)
                        .setBonus(bonus)
                        .setId(Long.parseLong(id))
                        .build();

                chefs.add(chef);
                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke: ", e);
        }
    }
}
