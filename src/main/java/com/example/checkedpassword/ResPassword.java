package com.example.checkedpassword;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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

    private File selectedFile;

    private String contents;

    public List<String> fileList = new ArrayList<>();

    @FXML
    void initialize() {
        final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);

        filChoose.setOnAction(new EventHandler<ActionEvent>() {

            @FXML @Override
            public void handle(ActionEvent event) {
                selectedFile = fileChooser.showOpenDialog(new Stage());
                nameFile.setText(selectedFile.getName());
                System.out.println(selectedFile);
            }
        });

        resultButton.setOnAction(actionEvent -> {
            System.out.println("hello");
            try {
                read();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(duplicate());
        });

    }

    private void read() throws Exception{
        contents = readUsingFiles(String.valueOf(selectedFile));
        System.out.println(contents);
        System.out.println(contents.length());
        String[] arr = contents.toString().split("\\s*(|-|_|—|,|\\.)\\s");
        Collections.addAll(fileList, arr);
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public List<String> duplicate() {
        Map<String,Integer > counter = new HashMap<>();
        for(String x : fileList){
            int newValue = counter.getOrDefault(x,0) + 1;
            counter.put(x,newValue);
        }
        return counter.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue()
                .reversed()).map(it -> {
            return it.getKey() + " = " + it.getValue();
        }).collect(Collectors.toList());
    }

    private void configuringFileChooser(FileChooser fileChooser) {
        // Set title for FileChooser
        fileChooser.setTitle("Select Some Files");

        // Set Initial Directory
        fileChooser.setInitialDirectory(new File("C:/"));

        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("TXT", "*.txt"));

    }
}