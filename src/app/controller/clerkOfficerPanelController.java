/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import static app.controller.mainPanelController.adminUsername;
import app.model.Database;
import app.model.DatabaseHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Bright
 */
public class clerkOfficerPanelController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Text day;
    @FXML
    private Text week;
    @FXML
    private Text date;
    @FXML
    private FontAwesomeIconView logout;
    @FXML
    private VBox pane;
    @FXML
    private Circle profile;
    @FXML
    private Text username;
    @FXML
    private JFXButton settingsBtn;
    @FXML
    private FontAwesomeIconView settingsIco;
    @FXML
    private JFXButton addUserBtn1;
    @FXML
    private FontAwesomeIconView useIco1;
    public static BorderPane root;
    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root = rootPane;
        adminUsername = username;
        setProfile(DatabaseHelper.ID);
        Tooltip closeApp = new Tooltip("Sign out");
        closeApp.setStyle("-fx-font-size:11");
        closeApp.setMinSize(20, 20);
        Tooltip.install(logout, closeApp);
        JFXDepthManager.setDepth(pane, 1);
        profile.setFill(new ImagePattern(new Image("/app/images/index.png"), 0, 0, 1, 1, true));
        setCenter("/app/view/employeeFileindex.fxml");

        ScheduledService<Void> scheduledService = new ScheduledService<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy  | hh: mm: ss aa");
                        Calendar calendar = new GregorianCalendar();
                        week.setText("WEEK " + calendar.get(Calendar.WEEK_OF_MONTH));
                        day.setText("DAY " + calendar.get(Calendar.DAY_OF_WEEK) + ",");
                        date.setText(dateFormat.format(new Date()));
                        return null;
                    }
                };
            }
        };
        scheduledService.setPeriod(Duration.seconds(1));
        Platform.runLater(() -> {
            scheduledService.start();
        });
    }

    private void setCenter(String fxmlUrl) {
        try {
            BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource(fxmlUrl));
            BorderPane.setMargin(borderPane, new Insets(0, 0, 0, 10));
            rootPane.setCenter(borderPane);
        } catch (IOException ex) {
            Logger.getLogger(clerkOfficerPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signOut(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure, you want to Sign Out?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("/app/view/login.fxml"));
                Scene scene = new Scene(root1);
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.getIcons().add(new Image("/app/images/filing_cabinet_80px.png"));
                stage1.initStyle(StageStyle.UNDECORATED);
                stage1.centerOnScreen();
                stage1.show();
            } catch (IOException ex) {
                Logger.getLogger(clerkOfficerPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    @FXML
    private void loadSettingsPanel(ActionEvent event) {
    }

    private void setProfile(int id) {
        String sql = "SELECT * FROM users WHERE Id = " + id + "";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            username.setText(resultSet.getString("Username"));
        } catch (SQLException ex) {
            Logger.getLogger(clerkOfficerPanelController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(clerkOfficerPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @FXML
    private void loadViewEmployeeFiles(ActionEvent event) {
        setCenter("/app/view/employeeFileindex.fxml");
    }

    @FXML
    private void loadSettings(MouseEvent event) {
        loadSettingsPanel(new ActionEvent());
    }

}
