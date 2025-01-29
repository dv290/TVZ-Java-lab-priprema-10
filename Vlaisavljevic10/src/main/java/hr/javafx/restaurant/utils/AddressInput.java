package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Address;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

//import static hr.javafx.production.main.Main.log;

/**
 * Pomoćna klasa za unos podataka o adresi. Ova klasa omogućava unos ulice, kućnog broja, grada i poštanskog broja.
 * Nakon unosa, metoda vraća objekt {@link Address} sa unesenim podacima.
 */
public class AddressInput {
    private AddressInput(){}

    public static void loadAddresses(Set<Address> addresses) {
        String filePath = "dat/addresses.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while(line != null && !line.trim().isEmpty()) {
                String street = br.readLine().trim();
                String houseNumber = br.readLine().trim();
                String city = br.readLine().trim();
                String postalCode = br.readLine().trim();

                Address address = new Address.AddressBuilder()
                        .setStreet(street)
                        .setHouseNumber(houseNumber)
                        .setCity(city)
                        .setPostalCode(postalCode)
                        .build();

                addresses.add(address);

                line = br.readLine();
            }
        } catch (IOException e) {
            //log.error("Došlo je do pogreške pri čitanju datoteke: ", e);
        }
    }
}
