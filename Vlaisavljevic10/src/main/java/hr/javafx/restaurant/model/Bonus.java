package hr.javafx.restaurant.model;

import java.math.BigDecimal;

/**
 * @param bonus record klasa za unos bonus iznosa za plaću zaposlenika u restoranima
 *              (konobari, kuhari i dostavljači). Iznos bonusa je predstavljen BigDecimal
 *              vrijednosti i unosti se nakon što je unesena plaća, neovisno o ugovoru (Contract)
 */

public record Bonus(BigDecimal bonus) {}
