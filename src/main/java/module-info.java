module com.suto.s4anext {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires javafx.swing;

    opens com.suto.s4anext to javafx.fxml;
    exports com.suto.s4anext;
}