package hr.javafx.restaurant.model;

import hr.javafx.restaurant.enums.ContractType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract extends Entity implements Serializable {

    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType contractType;
    private static Long idCounter = 1L;

    public Contract(BigDecimal salary, LocalDate startDate, LocalDate endDate, ContractType contractType) {
        super(idCounter);
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractType = contractType;
        idCounter++;
    }

    public void setId(Long newId) {
        super.setId(newId);
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }
}
