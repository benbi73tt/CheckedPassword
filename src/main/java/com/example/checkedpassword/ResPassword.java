package com.example.checkedpassword;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ResPassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button filChoose;

    @FXML
    private Label nameFile;

    @FXML
    private Button resultButton;

    @FXML
    void initialize() {
        final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
        filChoose.setOnAction(new EventHandler<ActionEvent>() {

            @FXML @Override
            public void handle(ActionEvent event) {
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                nameFile.setText(selectedFile.getName());
            }
        });

        resultButton.setOnAction(actionEvent -> {
            System.out.println("hello");

        });
    }

    private void configuringFileChooser(FileChooser fileChooser) {
        // Set title for FileChooser
        fileChooser.setTitle("Select Some Files");

        // Set Initial Directory
        fileChooser.setInitialDirectory(new File("C:/PC"));

        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("TXT", "*.txt"));

    }
}

//    void Str(){
//        final FileChooser fileChooser = new FileChooser();
//        configuringFileChooser(fileChooser);
//        resultButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("HEllo");
////                File selectedFile = fileChooser.showOpenDialog(stage);
////                System.out.println(selectedFile.getName());
//            }
//        });
//    }
//    private void configuringFileChooser(FileChooser fileChooser) {
//        // Set title for FileChooser
//        fileChooser.setTitle("Select Some Files");
//
//        // Set Initial Directory
//        fileChooser.setInitialDirectory(new File("C:/PC"));
//
//        fileChooser.getExtensionFilters().addAll(//
//                new FileChooser.ExtensionFilter("TXT", "*.txt"));
//
//    }