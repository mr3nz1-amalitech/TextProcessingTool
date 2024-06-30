module com.lms.textprocessingtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires jdk.unsupported.desktop;

    opens com.lms.textprocessingtool to javafx.fxml;
    exports com.lms.textprocessingtool;
    exports com.lms.textprocessingtool.Controllers;
    opens com.lms.textprocessingtool.Controllers to javafx.fxml;
}