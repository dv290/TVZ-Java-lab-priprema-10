module hr.javafx.production.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.desktop;
    requires java.sql;


    opens hr.javafx.main to javafx.fxml;
    exports hr.javafx.main;
    exports hr.javafx.controller;
    opens hr.javafx.controller to javafx.fxml;
}