module com.example.checkedpassword {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.checkedpassword to javafx.fxml;
    exports com.example.checkedpassword;
}