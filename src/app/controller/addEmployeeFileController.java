/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.model.DatabaseHelper;
import app.model.Employee;
import app.util.ShowAlert;
import app.util.ShowTrayNotification;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Bright
 */
public class addEmployeeFileController implements Initializable {

    @FXML
    private AnchorPane pane2;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXComboBox<String> department;
    @FXML
    private JFXComboBox<String> staffType;
    public static Boolean editMode = false;
    int employee_id = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        department.setItems(FXCollections.observableArrayList(
                "Roads", "Buldings", "Marine", "Civil Aviation", "Road Traffic", "P.V.H.O", "Railways", "Headquarters"
        ));
        staffType.setItems(FXCollections.observableArrayList(
                "Senior Staff (C-K)", "Juniour Staff (L-R)"
        ));
    }

    @FXML
    private void cancelSaveEmployeeFileFunction(ActionEvent event) {
        try {
            BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/app/view/employeeFileindex.fxml"));
            BorderPane.setMargin(borderPane, new Insets(0, 0, 0, 10));
            clerkOfficerPanelController.root.setCenter(borderPane);
        } catch (IOException ex) {
            Logger.getLogger(employeeIndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveEmployeeFile(ActionEvent event) {
        DatabaseHelper helper = new DatabaseHelper();
        String staff = "";
        String tableName = "";
        if (validateFields()) {
            switch (staffType.getValue()) {
                case "Senior Staff (C-K)": {
                    staff += "1A";
                    break;
                }
                case "Juniour Staff (L-R)": {
                    staff += "1B";
                    break;
                }
            }
            switch (department.getValue()) {
                case "Civil Aviation": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "Aviationss";
                    } else {
                        tableName = "Aviationsj";
                    }
                    break;
                }
                case "Road Traffic": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "RoadTraffics";
                    } else {
                        tableName = "RoadTrafficj";
                    }
                    break;
                }
                case "P.V.H.O": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "PVHOs";
                    } else {
                        tableName = "PVHOj";
                    }
                    break;
                }
                case "Railways": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "Railwayss";
                    } else {
                        tableName = "Railwaysj";
                    }
                    break;
                }
                case "Headquarters": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "Headquarterss";
                    } else {
                        tableName = "Headquartersj";
                    }
                    break;
                }
                case "Marine": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "Mariness";
                    } else {
                        tableName = "Marinesj";
                    }
                    break;
                }
                case "Buldings": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "Buldingss";
                    } else {
                        tableName = "Buldingsj";
                    }
                    break;
                }
                case "Roads": {
                    if (staffType.getValue().equals("Senior Staff (C-K)")) {
                        tableName = "Roadss";
                    } else {
                        tableName = "Roadsj";
                    }
                    break;
                }
            }
            if (editMode) {
                Employee employee = new Employee(employee_id, firstName.getText(), lastName.getText(), department.getValue(), staffType.getValue());
                if (helper.updateEmployee(employee, tableName)) {
                    clearFields();
                    cancelSaveEmployeeFileFunction(new ActionEvent());
                    ShowTrayNotification notification = new ShowTrayNotification("Message", "Employee file succssfully updated", NotificationType.SUCCESS);
                } else {
                    ShowTrayNotification notification = new ShowTrayNotification("Error", "Failed to update the employee file", NotificationType.ERROR);
                }
            } else {
                String fileName = "MTPW|PF|" + department.getValue() + "|" + staff + "|";
                Employee employee = new Employee(firstName.getText(), lastName.getText(), department.getValue(), fileName);
                if (helper.addEmployeeFile(employee, tableName)) {
                    clearFields();
                    cancelSaveEmployeeFileFunction(new ActionEvent());
                    ShowTrayNotification notification = new ShowTrayNotification("Message", "Employee file succssfully added", NotificationType.SUCCESS);
                } else {
                    ShowTrayNotification notification = new ShowTrayNotification("Error", "Failed to add the employee file", NotificationType.ERROR);
                }
            }
        }
    }

    private Boolean validateFields() {
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || department.getValue() == null || staffType.getValue() == null) {
            ShowAlert alert = new ShowAlert(Alert.AlertType.ERROR, "Fields validation", "Please enter in all fields");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void clearFields() {
        firstName.clear();
        lastName.clear();
        department.setValue(null);
        staffType.setValue(null);
        department.setEditable(true);
        staffType.setEditable(true);
    }

    public void inflateUI(employeeIndexController.Employee employee) {
        employee_id = employee.getId();
        firstName.setText(employee.getFirstName());
        lastName.setText(employee.getLastName());
        department.setValue(employee.getDepartment());
        staffType.setValue(employee.getStaffType());
        department.setDisable(true);
        staffType.setDisable(true);
    }

}
