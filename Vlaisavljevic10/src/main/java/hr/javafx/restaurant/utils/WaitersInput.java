package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos podataka vezanih za konobare u sustav.
 * Omogućuje unos podataka za konobare, uključujući ime, prezime, ugovor, bonus i provjeru dupliciranja konobara.
 */

public class WaitersInput {
    private WaitersInput(){}

    public static void loadWaiters(Set<Waiter> waiters, List<Contract> contracts, AtomicInteger contractIndex) {
        String filePath = "dat/waiters.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ( line!=null && !line.trim().isEmpty()) {
                String id = line.trim();
                String firstName = br.readLine().trim();
                String lastName = br.readLine().trim();

                Long contractId = Long.parseLong(br.readLine().trim());
                Contract contract = null;
                if (contractIndex.get() < contracts.size()) {
                    contract = contracts.get(contractIndex.getAndIncrement());
                }

                if (contract == null) {
                    log.error("Nije pronađen ID: {}", contractId);
                    break;
                }

                Bonus bonus = new Bonus(new BigDecimal(br.readLine().trim()));

                Waiter waiter = new Waiter.WaiterBuilder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setContract(contract)
                        .setBonus(bonus)
                        .setId(Long.parseLong(id))
                        .build();

                waiters.add(waiter);

                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke: ", e);
        }
    }
}
