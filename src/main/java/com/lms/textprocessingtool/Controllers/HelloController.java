package com.lms.textprocessingtool.Controllers;

import com.lms.textprocessingtool.CollectionsActivity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    public TextArea textArea;
    public TextField regexInput;
    public Button searchButton;
    public Button replaceButton;
    public Button matchButton;
    public TextField replaceText;
    public Text resultField;
    public ComboBox dataStructuresComboBox;

    @FXML
    public Label welcomeText;
    public TextField collectionInputData;
    public ComboBox collectionDataComboBox;

    @FXML
    void initialize() {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("ArrayList");
            list.add("Set");
            list.add("Map");
            CollectionsActivity collection = new CollectionsActivity();
            dataStructuresComboBox.getItems().clear();
            dataStructuresComboBox.setItems(FXCollections.observableList(list));


            String dataStructure = dataStructuresComboBox.getValue().toString();
            loadCollectionValues(dataStructure);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    @FXML
    public void onSearchButtonClick() {
        String result = search();
        resultField.setText(result != "" ? "Result found: " + result : "Not found");
    }

    @FXML
    public void onReplaceButtonClick() {
        String result = search();
        resultField.setText("String with replaced sections: " + textArea.getText().replaceAll(regexInput.getText(), replaceText.getText()));
    }

    public String search() {
        Pattern pattern = Pattern.compile(regexInput.getText());
        Matcher matcher = pattern.matcher(textArea.getText());

        return matcher.find() ? matcher.group() : "";
    }

    public void onDataStructureChange() throws IOException, ClassNotFoundException {
        System.out.println("Changed");
        System.out.println(dataStructuresComboBox.getValue().toString());
        loadCollectionValues(dataStructuresComboBox.getValue().toString());
    }

    public void loadCollectionValues(String collectionName) throws IOException, ClassNotFoundException {
        CollectionsActivity collectionsActivity = new CollectionsActivity();

        if (collectionName == "ArrayList") {
            ArrayList<String> arrayList = (ArrayList<String>) collectionsActivity.deserialize("ArrayList.txt");
            collectionDataComboBox.getItems().clear();
            collectionDataComboBox.setItems(FXCollections.observableList(arrayList));
        } else if (collectionName == "Set") {
            Set<String> hashSet = (HashSet<String>) collectionsActivity.deserialize("HashSet.txt");
            collectionDataComboBox.getItems().clear();
            collectionDataComboBox.setItems(FXCollections.observableList(new ArrayList<>(hashSet)));
        } else {
            HashMap<Integer, String> hashMap = (HashMap<Integer, String>) collectionsActivity.deserialize("HashMap.txt");
            collectionDataComboBox.getItems().clear();

            collectionDataComboBox.setItems(FXCollections.observableList(new ArrayList<>(hashMap.entrySet())));
        }
    }

    public void handleDataStructureChange(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String dataStructure = null;
        try {
            dataStructure = dataStructuresComboBox.getValue().toString();

        } catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Can't add to an unselected collection");
            alert.showAndWait();
        }
        CollectionsActivity collectionsActivity = new CollectionsActivity();


        if (dataStructure.equals("ArrayList")) {
            try {
                ArrayList<String> arrayList = (ArrayList<String>) collectionsActivity.deserialize("ArrayList.txt");
                arrayList.add(collectionInputData.getText());
                collectionsActivity.serialize("ArrayList.txt", arrayList);
            } catch (IOException error) {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(collectionInputData.getText());
                collectionsActivity.serialize("ArrayList.txt", arrayList);
            }
            loadCollectionValues("ArrayList");
        } else if (dataStructure.equals("Set")) {
            try {
                Set<String> hashSet = (HashSet<String>) collectionsActivity.deserialize("HashSet.txt");
                hashSet.add(collectionInputData.getText());
                collectionsActivity.serialize("HashSet.txt", hashSet);
            } catch (IOException error) {
                Set<String> hashSet = new HashSet<String>();
                hashSet.add(collectionInputData.getText());
                collectionsActivity.serialize("HashSet.txt", hashSet);
            }
            loadCollectionValues("Set");
        } else {
            try {
                HashMap<Integer, String> hashMap = (HashMap<Integer, String>) collectionsActivity.deserialize("HashMap.txt");
                hashMap.put(hashMap.size(), collectionInputData.getText());
                collectionsActivity.serialize("HashMap.txt", hashMap);
            } catch (IOException error) {
                HashMap<Integer, String> hashMap = new HashMap<>();
                hashMap.put(hashMap.size(), collectionInputData.getText());
                collectionsActivity.serialize("HashMap.txt", hashMap);
            }
            loadCollectionValues("HashMap");
        }

    }

    public void onCollectionValueChange(ActionEvent actionEvent) {
        String value = collectionDataComboBox.getValue().toString();
        collectionInputData.setText(value);
    }

    public void handleSubmitTextChange(ActionEvent actionEvent) throws IOException {
        String dataStructure = dataStructuresComboBox.getValue().toString();
        if (dataStructure.equals("ArrayList")) {
            String newData = collectionInputData.getText();
            String selectedValue = collectionDataComboBox.getValue().toString();
            List<String> list = collectionDataComboBox.getItems();
            list.set(list.indexOf(selectedValue), newData);
            ArrayList<String> arrayList = new ArrayList<String>(list);
            CollectionsActivity collectionsActivity = new CollectionsActivity();
            collectionsActivity.serialize("ArrayList.txt", arrayList);
            collectionInputData.setText("");
        } else if (dataStructure.equals("Set")) {
            String newData = collectionInputData.getText();
            String selectedValue = collectionDataComboBox.getValue().toString();
            List<String> list = collectionDataComboBox.getItems();
            list.set(list.indexOf(selectedValue), newData);
            Set<String> hashSet = new HashSet<>(list);
            CollectionsActivity collectionsActivity = new CollectionsActivity();
            collectionsActivity.serialize("HashSet.txt", hashSet);
            collectionInputData.setText("");

        }
    }

    public void handleDelete() throws IOException {
        String dataStructure = dataStructuresComboBox.getValue().toString();
        if (dataStructure.equals("ArrayList")) {
            CollectionsActivity collectionsActivity = new CollectionsActivity();
            String selectedvalue = collectionDataComboBox.getValue().toString();
            List<String> list = collectionDataComboBox.getItems();
            list.remove(selectedvalue);
            ArrayList<String> arrayList = new ArrayList<String>();
            collectionsActivity.serialize("ArrayList.txt", arrayList);
        } else if (dataStructure.equals("Set")) {
            CollectionsActivity collectionsActivity = new CollectionsActivity();
            String selectedvalue = collectionDataComboBox.getValue().toString();
            List<String> list = collectionDataComboBox.getItems();
            list.remove(selectedvalue);
            Set<String> arrayList = new HashSet<String>();
            collectionsActivity.serialize("HashSet.txt", arrayList);
        } else if (dataStructure.equals("HashMap")) {
            CollectionsActivity collectionsActivity = new CollectionsActivity();
            String selectedvalue = collectionDataComboBox.getValue().toString();
            HashMap<Integer, String> hashMap = (HashMap<Integer, String>) collectionDataComboBox.getItems();
            hashMap.remove(Integer.valueOf(selectedvalue.split("=")[0]));
            collectionsActivity.serialize("HashMap.txt", hashMap);
        }
    }
}