module profit.sysanal {
    requires javafx.controls;
    requires javafx.fxml;


    opens profit.sysanal to javafx.fxml;
    exports profit.sysanal;
}