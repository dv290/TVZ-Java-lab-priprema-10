package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Category;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos kategorija. Ova klasa omogućava unos naziva i opisa kategorije.
 * Također provodi provjeru da li uneseni naziv već postoji u postojećim kategorijama.
 */

public class CategoryInput {
    private CategoryInput(){} //privatni default constructor spriječava instanciranje objekta tipa CategoryInput
    /**
     * Čita podatke o kategorijama iz datoteke i dodaje ih u pruženi popis kategorija.
     * <p>
     * Metoda čita podatke iz datoteke smještene na lokaciji "dat/categories.txt". Za svaku kategoriju očekuju se dvije linije:
     * prva linija sadrži naziv kategorije, a druga opis. Samo kategorije koje imaju nenulja naziva i opisa bit će
     * dodane u pruženi popis.
     * </p>
     *
     * @param categories popis u koji će biti dodane kategorije
     */
    public static void loadCategories(List<Category> categories) {
        String filePath = "dat/categories.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ( line != null && !line.trim().isEmpty() ) {
                String name = br.readLine().trim();
                String description = br.readLine().trim();

                if (!name.isEmpty() && !description.isEmpty()) {
                    categories.add(new Category.CategoryBuilder()
                            .setName(name)
                            .setDescription(description)
                            .build());
                }
                line = br.readLine(); //updatea liniju, cita iduci redak nakon descriptiona
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke: ", e);
        }
    }
}