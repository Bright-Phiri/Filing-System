/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.model.Database;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Bright
 */
public class loginController implements Initializable {

    @FXML
    private ImageView imageView;
    ScheduledService scheduledService;
    ScheduledService sendMail;
    @FXML
    private BorderPane rootPane;
    public static BorderPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root = rootPane;
        Database.checkTables("users", "CREATE TABLE `efs`.`users` (\n"
                + "`Id` INT NOT NULL AUTO_INCREMENT ,\n"
                + "`FirstName` VARCHAR( 40 ) NULL ,\n"
                + "`LastName` VARCHAR( 40 )  NULL ,\n"
                + "`Username` VARCHAR( 40 ) NOT NULL ,\n"
                + "`Email` VARCHAR( 40 ) NULL ,\n"
                + "`Phone` VARCHAR( 20 ) NOT NULL ,\n"
                + "`Role` VARCHAR( 20 ) NULL ,\n"
                + "`Password` VARCHAR( 50 ) NOT NULL ,\n"
                + "PRIMARY KEY ( `Id` ) \n"
                + ") ENGINE = InnoDB;");
        Database.checkTables("Roadsj", "CREATE TABLE `efs`.`Roadsj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Roadss", "CREATE TABLE `efs`.`Roadss` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Buldingsj", "CREATE TABLE `efs`.`Buldingsj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Buldingss", "CREATE TABLE `efs`.`Buldingss` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Marinesj", "CREATE TABLE `efs`.`Marinesj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Mariness", "CREATE TABLE `efs`.`Mariness` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Aviationsj", "CREATE TABLE `efs`.`Aviationsj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Aviationss", "CREATE TABLE `efs`.`Aviationss` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("RoadTrafficj", "CREATE TABLE `efs`.`RoadTrafficj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("RoadTraffics", "CREATE TABLE `efs`.`RoadTraffics` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("PVHOj", "CREATE TABLE `efs`.`PVHOj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("PVHOs", "CREATE TABLE `efs`.`PVHOs` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL, `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Railwaysj", "CREATE TABLE `efs`.`Railwaysj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL ,`file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Railwayss", "CREATE TABLE `efs`.`Railwayss` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Headquartersj", "CREATE TABLE `efs`.`Headquartersj` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        Database.checkTables("Headquarterss", "CREATE TABLE `efs`.`Headquarterss` ( `id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(40) NOT NULL , `last_name` VARCHAR(40) NOT NULL , `department` VARCHAR(20) NOT NULL , `file_name` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ");
        try {
            VBox box = (VBox) FXMLLoader.load(getClass().getResource("/app/view/loginForm.fxml"));
            rootPane.setRight(box);
        } catch (IOException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        scheduledService = new ScheduledService() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        setImage();
                        return null;
                    }
                };
            }
        };
        scheduledService.setDelay(Duration.ONE);
        scheduledService.setRestartOnFailure(true);
        scheduledService.setPeriod(Duration.seconds(8));
        Platform.runLater(() -> {
            scheduledService.start();
        });

    }

    private void setImage() {
        String image1 = "1.png";
        String image2 = "2.png";
        String image3 = "3.png";
        String image4 = "4.png";
        String image5 = "5.png";
        String image6 = "6.png";

        String image = null;
        int number = 1 + (int) (Math.random() * 6);
        switch (number) {
            case 1: {
                image = image1;
                break;
            }
            case 2: {
                image = image2;
                break;
            }
            case 3: {
                image = image3;
                break;
            }
            case 4: {
                image = image4;
                break;
            }
            case 5: {
                image = image5;
                break;
            }
            case 6: {
                image = image6;
                break;
            }
        }
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), imageView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        imageView.setImage(new Image("/app/images/" + image));
    }
}
