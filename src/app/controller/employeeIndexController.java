/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.model.Database;
import app.util.ShowAlert;
import app.util.ShowTrayNotification;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Bright
 */
public class employeeIndexController implements Initializable {

    @FXML
    private TableView<Employee> employeeFilesTable;
    @FXML
    private TableColumn<Employee, String> firstName;
    @FXML
    private TableColumn<Employee, String> lastName;
    @FXML
    private TableColumn<Employee, String> department;
    @FXML
    private TableColumn<Employee, String> filename;
    @FXML
    private TableColumn<Employee, HBox> action;
    @FXML
    private AnchorPane pane1;
    @FXML
    private JFXComboBox<String> departments;
    @FXML
    private TableColumn<Employee, HBox> file;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private final ObservableList<Employee> data = FXCollections.observableArrayList();
    @FXML
    private JFXTextField searchEmployeeTxtField;
    @FXML
    private JFXComboBox<String> staffType;
    @FXML
    private Text totalRecords;
    String stafftype = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staffType.setItems(FXCollections.observableArrayList(
                "Senior Staff (C-K)", "Juniour Staff (L-R)"
        ));
        departments.setItems(FXCollections.observableArrayList(
                "Roads", "Buldings", "Marine", "Civil Aviation", "Road Traffic", "P.V.H.O", "Railways", "Headquarters"
        ));
        initColumns();
        loadData("Headquartersj");
    }

    @FXML
    private void loadAddEmployeeFile(ActionEvent event) {
        try {
            BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/app/view/addEmployeeFile.fxml"));
            BorderPane.setMargin(borderPane, new Insets(0, 0, 0, 10));
            clerkOfficerPanelController.root.setCenter(borderPane);
        } catch (IOException ex) {
            Logger.getLogger(employeeIndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchRecord(KeyEvent event) {
        FilteredList<Employee> employees = new FilteredList<>(data, p -> true);
        searchEmployeeTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            employees.setPredicate((Predicate<? super Employee>) employee -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String filterToLowerCase = newValue.toLowerCase();
                if (employee.getFirstName().toLowerCase().contains(filterToLowerCase)) {
                    return true;
                }
                if (employee.getLastName().toLowerCase().contains(filterToLowerCase)) {
                    return true;
                }
                if (employee.getDepartment().toLowerCase().contains(filterToLowerCase)) {
                    return true;
                }
                if (employee.getFilename().toLowerCase().contains(filterToLowerCase)) {
                    return true;
                }
                return false;
            });
            SortedList<Employee> sortedList = new SortedList<>(employees);
            sortedList.comparatorProperty().bind(employeeFilesTable.comparatorProperty());
            employeeFilesTable.setItems(sortedList);
        });
    }

    private HBox setFileIcon() {
        HBox hBox = new HBox(5);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_ALT);
        icon.setSize("17");
        icon.setFill(Color.web("#599fff"));
        hBox.getChildren().addAll(icon);
        return hBox;
    }

    private HBox setActionButtons(int userId, String tableName) {
        String query = "delete from " + tableName + " where Id = ?";
        String query1 = "select * from " + tableName + " where Id = ?";
        HBox hBox = new HBox(5);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
        icon.setSize("17");
        icon.setFill(Color.web("#66ddff"));
        HBox.setMargin(icon, new Insets(2, 0, 0, 0));
        icon.setId(String.valueOf(userId));
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    connection = Database.connect();
                    preparedStatement = connection.prepareStatement(query1);
                    preparedStatement.setInt(1, userId);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    int length = tableName.length();
                    int index = length - 1;
                    if (tableName.charAt(index) == 'j') {
                        stafftype = "Juniour Staff (L-R)";
                    } else {
                        stafftype = "Senior Staff (C-K)";
                    }
                    Employee employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), stafftype, resultSet.getString(5));
                    FXMLLoader xMLLoader = new FXMLLoader();
                    xMLLoader.setLocation(getClass().getResource("/app/view/addEmployeeFile.fxml"));
                    Parent parent = xMLLoader.load();
                    BorderPane.setMargin(parent, new Insets(0, 0, 0, 10));
                    addEmployeeFileController employeeFileController = (addEmployeeFileController) xMLLoader.getController();
                    employeeFileController.inflateUI(employee);
                    clerkOfficerPanelController.root.setCenter(parent);
                    addEmployeeFileController.editMode = Boolean.TRUE;
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(viewUsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hBox.getChildren().addAll(icon);
        return hBox;
    }

    private void initColumns() {
        file.setCellValueFactory(new PropertyValueFactory<>("file"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        filename.setCellValueFactory(new PropertyValueFactory<>("filename"));
        action.setCellValueFactory(new PropertyValueFactory<>("controlsPane"));
    }

    private void loadData(String tableName) {
        data.clear();
        String sql = "SELECT * FROM " + tableName + "";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data.add(new Employee(resultSet.getInt(1), setFileIcon(), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), setActionButtons(resultSet.getInt(1), tableName)));
            }
            employeeFilesTable.setItems(data);
            totalRecords.setText(String.valueOf(data.size()));
        } catch (SQLException ex) {
            Logger.getLogger(employeeIndexController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(employeeIndexController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void loadDepartmentsRecords(ActionEvent event) {
        String tableName = "";
        if (staffType.getValue() == null) {
            ShowAlert alert = new ShowAlert(Alert.AlertType.INFORMATION, "Information", "Please select the staff type");
        } else {
            if (departments.getValue() != null) {
                switch (departments.getValue()) {
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
                loadData(tableName);
            } else {
            }
        }
    }

    @FXML
    private void clearSelection(MouseEvent event) {
        departments.getSelectionModel().clearSelection();
    }

    public class Employee {

        SimpleIntegerProperty id;
        HBox file;
        SimpleStringProperty firstName;
        SimpleStringProperty lastName;
        SimpleStringProperty department;
        SimpleStringProperty staffType;
        SimpleStringProperty filename;
        HBox controlsPane;

        public Employee(int id, HBox file, String firstName, String lastName, String department, String filename, HBox controlsPane) {
            this.id = new SimpleIntegerProperty(id);
            this.file = file;
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.department = new SimpleStringProperty(department);
            this.filename = new SimpleStringProperty(filename);
            this.controlsPane = controlsPane;
        }

        public Employee(int id, String firstName, String lastName, String department, String staffType, String filename) {
            this.id = new SimpleIntegerProperty(id);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.department = new SimpleStringProperty(department);
            this.staffType = new SimpleStringProperty(staffType);
            this.filename = new SimpleStringProperty(filename);
        }

        public int getId() {
            return id.get();
        }

        public HBox getFile() {
            return file;
        }

        public String getFirstName() {
            return firstName.get();
        }

        public String getLastName() {
            return lastName.get();
        }

        public String getDepartment() {
            return department.get();
        }

        public String getStaffType() {
            return staffType.get();
        }

        public String getFilename() {
            return filename.get();
        }

        public HBox getControlsPane() {
            return controlsPane;
        }
    }
}
