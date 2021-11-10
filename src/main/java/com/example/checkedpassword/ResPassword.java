package com.example.checkedpassword;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

            @FXML
            @Override
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

    private void read() throws Exception {
        contents = readUsingFiles(String.valueOf(selectedFile));
        System.out.println(contents);
        System.out.println(contents.length());
        String[] sshd;
        String sshdString = "";
        String[] ipAddress;
        String ipAddressString = "";
        String[] ports;
        String portsString = "";

        String[] arr = contents.toString().split("\\s*(|-|_|—|,|\\.)\\s");

        Pattern ipPattern = Pattern.compile("(\\d{0,3}\\.){3}\\d{0,3}");
        Pattern sshdPattern = Pattern.compile("\\b*(sshd\\[\\d+\\])");
        Pattern portPattern = Pattern.compile("\\b(port)\\b");

        for (int i = 0; i < arr.length; i++) {
            Matcher ipMatcher = ipPattern.matcher(arr[i]);
            Matcher sshdMatcher = sshdPattern.matcher(arr[i]);
            Matcher portsMatcher = portPattern.matcher(arr[i]);
            if (ipMatcher.find()) {
                ipAddressString += ipMatcher.group() + " ";
            }
            while (sshdMatcher.find()) {
                String word = sshdMatcher.group();
                if (word.toLowerCase().startsWith("s"))
                    sshdString += word + " ";
            }
            while (portsMatcher.find()){
                String word = portsMatcher.group();
                if (word.toLowerCase().startsWith("p"))
                    portsString += arr[i+1]+" ";
            }
        }
        ipAddress = ipAddressString.split("\\s*(|-|_|—|,|\\.)\\s");
        sshd = sshdString.split("\\s*(|-|_|—|,|\\.)\\s");
        ports = portsString.split("\\s*(|-|_|—|,|\\.)\\s");

        Collections.addAll(fileList, ipAddress);
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public List<String> duplicate() {
        Map<String, Integer> counter = new HashMap<>();
        for (String x : fileList) {
            int newValue = counter.getOrDefault(x, 0) + 1;
            counter.put(x, newValue);
        }
        return counter.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()
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