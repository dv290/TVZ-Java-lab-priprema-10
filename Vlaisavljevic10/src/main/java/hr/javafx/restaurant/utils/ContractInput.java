package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Contract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos podataka o ugovoru. Ova klasa omogućava unos plaće, vrste zaposlenja te datuma početka i kraja rada.
 */

public class ContractInput {
    private ContractInput(){}

    public static void loadContracts(List<Contract> contracts) {
        String filePath = "dat/contracts.txt";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ( line != null && !line.trim().isEmpty() ) {
                BigDecimal salary = new BigDecimal(br.readLine().trim());

                String startDateString = br.readLine();
                LocalDate startDate=null;
                if(startDateString != null && !startDateString.trim().isEmpty()) {
                    startDate = LocalDate.parse(startDateString, dateFormat);
                }

                String endDateString = br.readLine();
                LocalDate endDate=null;
                if(endDateString != null && !endDateString.trim().isEmpty()) {
                    endDate = LocalDate.parse(endDateString, dateFormat);
                }

                String contractTypeString = br.readLine();
                ContractType contractType;
                if (contractTypeString.equals("PART_TIME")) {
                    contractType = ContractType.PART_TIME;
                } else {
                    contractType = ContractType.FULL_TIME;
                }

                contracts.add(new Contract(salary,startDate,endDate,contractType));

                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke contracts.txt: ", e);
        }
    }
}
