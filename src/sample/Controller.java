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
    //observablelist is like an array but it will automatically update the ListView when it is updated

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
            nameText.setText(""); // setText will return the text back to blank after adding. if i didn't have setText
            // then the text field would continue to show the last text you entered.
            phoneText.setText("");
            emailText.setText("");
            saveData(contacts); // name, phone, email - if any are empty then do nothing, else add a new contact
            //object using the get text method

        }

    }

    public void removeItem() throws IOException{
        Contact contactRemove = (Contact) list.getSelectionModel().getSelectedItem(); // getselectionmodel = tracks selection
        contacts.remove(contactRemove);
        saveData(contacts); // need to figure out why brackets are empty
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.setItems(contacts); // initialize will run right when the app starts

        //populate the list before set items

    }

    // Method to save json file
    public void saveData(ObservableList<Contact> contact) throws IOException {
        JsonSerializer s = new JsonSerializer();
        String json = s.include("*").serialize(contact);

        File f = new File("contact.json");
        FileWriter fw = new FileWriter(f);
        fw.write(json);
        fw.close();
        // NOTE: in order to save json data you need to have getters and setters on your fields or you will have
        // empty brackets in your json file.
    }

    public static Contact loadData(ObservableList<Contact> contact) throws FileNotFoundException {
        File f = new File("contact.json");
        Scanner s = new Scanner(f);
        s.useDelimiter("\\Z");
        String contents = s.next();

        JsonParser p = new JsonParser();
        return p.parse(contents, Contact.class);

    }


}

