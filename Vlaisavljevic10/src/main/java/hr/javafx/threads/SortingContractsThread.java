package hr.javafx.threads;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Contract;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import javafx. util. Duration;
import java.util.List;
import java.util.stream.Collectors;

import static hr.javafx.main.Main.log;

public class SortingContractsThread implements Runnable {

    private final Data data = Data.getInstance();
    private final TableView<Contract> contractTableView;
    private volatile boolean running = true;
    private Timeline salaryTimeLine;

    public SortingContractsThread(TableView<Contract> contractTableView) {
        this.contractTableView = contractTableView;
    }

    @Override
    public void run() {
        salaryTimeLine = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    if (!running) {return;}

                    try {
                        List<Contract> contracts = data.getContracts();

                        List<Contract> sortedContracts = contracts.stream()
                                .sorted((c1, c2) -> c2.getSalary().compareTo(c1.getSalary()))
                                .collect(Collectors.toList());

                        Platform.runLater(() -> {
                            ObservableList<Contract> observableList = FXCollections.observableArrayList(sortedContracts);
                            contractTableView.setItems(observableList);
                        });

                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }

                }),
                new KeyFrame(Duration.seconds(1))
        );

        salaryTimeLine.setCycleCount(Timeline.INDEFINITE);
        salaryTimeLine.play();
    }

    public void stopThread() {
        running = false;
        if (salaryTimeLine != null) {
            salaryTimeLine.stop();
        }
    }
}