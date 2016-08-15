package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    ListView list;

    @FXML
    TextField nameText;

    @FXML
    TextField phoneText;

    @FXML
    TextField emailText;

    public void addItem() throws IOException {
        if(!nameText.getText().equals("") && !phoneText.getText().equals("") && !emailText.getText().equals("")) {
            contacts.add(new Contact(nameText.getText(), phoneText.getText(), emailText.getText()));
            nameText.setText("");
            phoneText.setText("");
            emailText.setText("");
            saveData(contacts); // need to figure out why brackets are empty
        }

    }

    public void removeItem() {
        Contact contactRemove = (Contact) list.getSelectionModel().getSelectedItem();
        contacts.remove(contactRemove);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setItems(contacts); // performing method call.  method is called set items
        //and observable array list of items is what is passing into the method
        //ties our data to our control

    }

    // Method to save json file
    public void saveData(ObservableList<Contact> contact) throws IOException {
        JsonSerializer s = new JsonSerializer();
        String json = s.include("*").serialize(contact);

        File f = new File("contact.json");
        FileWriter fw = new FileWriter(f);
        fw.write(json);
        fw.close();
    }

    public static void loadData() throws FileNotFoundException {
        File f = new File("contact.json");
        Scanner s = new Scanner(f);
        s.useDelimiter("\\Z");
        String contents = s.next();

        JsonParser p = new JsonParser();
        Contact m = p.parse(contents, Contact.class);
        System.out.println(m); // now how do i override toString
    }


}

