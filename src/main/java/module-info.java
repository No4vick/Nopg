module com.example.nopg {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.no4nick.nopg to javafx.fxml;
    exports com.no4nick.nopg;
}