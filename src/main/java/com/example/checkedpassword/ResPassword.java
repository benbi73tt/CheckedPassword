package com.example.checkedpassword;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
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
    private TabPane tPn;

    @FXML
    private TextArea txtIPAddress;

    @FXML
    private TextArea txtPorts;

    @FXML
    private TextArea txtSshd;

    private File selectedFile;

    private String contents;

    private String[] ports;

    private String[] sshd;

    private String[] ipAddress;

    private static String apply(Map.Entry<String, Integer> it) {
        return it.getKey() + " = " + it.getValue();
    }

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
            txtSshd.setText(duplicate(sshd));
            txtIPAddress.setText(duplicate(ipAddress));
            txtPorts.setText(duplicate(ports));
        });
    }

    private void read() throws Exception {
        contents = readUsingFiles(String.valueOf(selectedFile));

        String sshdString = "";
        String ipAddressString = "";
        String portsString = "";

        String[] arr = contents.split("\\s*(|-|_|—|,|\\.)\\s");

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
            while (portsMatcher.find()) {
                String word = portsMatcher.group();
                if (word.toLowerCase().startsWith("p"))
                    portsString += arr[i + 1] + " ";
            }
        }
        ipAddress = ipAddressString.split("\\s*(|-|_|—|,|\\.)\\s");
        sshd = sshdString.split("\\s*(|-|_|—|,|\\.)\\s");
        ports = portsString.split("\\s*(|-|_|—|,|\\.)\\s");


    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public String duplicate(String[] arr) {
        List<String> fileList = new ArrayList<>();
        Collections.addAll(fileList, arr);
        Map<String, Integer> counter = new HashMap<>();
        for (String x : fileList) {
            int newValue = counter.getOrDefault(x, 0) + 1;
            counter.put(x, newValue);
        }
        String s = counter.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()
                .reversed()).map(ResPassword::apply).collect(Collectors.toList()).subList(0, 10).toString();
        return s.replaceAll(",", "  ");
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