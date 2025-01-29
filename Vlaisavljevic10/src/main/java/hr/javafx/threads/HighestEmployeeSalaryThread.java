package hr.javafx.threads;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Person;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HighestEmployeeSalaryThread implements Runnable{
    private final Data data = Data.getInstance();
    private final Label highestPaidEmployeeLabel;

    public HighestEmployeeSalaryThread(Label highestPaidEmployeeLabel) {
        this.highestPaidEmployeeLabel = highestPaidEmployeeLabel;
    }

    @Override
    public void run() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e->{

                    List<Person> employees = new ArrayList<>();
                    employees.addAll(data.getChefs());
                    employees.addAll(data.getWaiters());
                    employees.addAll(data.getDeliverers());

                    Optional<Person> highestPaidEmployee = employees.stream()
                            .max(Comparator.comparing(person -> person.getContract().getSalary()));

                    highestPaidEmployee.ifPresent(employee -> {
                        String fullName = employee.getFirstName() + " " + employee.getLastName() + "("+employee.getProfession()+")";


                            Platform.runLater(() -> highestPaidEmployeeLabel.setText("Najplaćeniji zaposlenik: " + fullName));

                    });
                }
                ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
