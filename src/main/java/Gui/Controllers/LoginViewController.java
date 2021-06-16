package Gui.Controllers;

import Be.User;
import Bll.HandlingPassword;
import Exceptions.DatabaseException;
import Gui.Models.KPIcollectionModel;
import Gui.Models.TemplateModel;
import Gui.Models.TileCellModel;
import Gui.Models.UserModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private JFXButton signInButton;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView cowView;
    @FXML
    private ImageView arlaLogoView;
    @FXML
    private Pane cowPane;
    @FXML
    private Pane logoPane;



    private UserModel userModel;
    private TemplateModel templateModel;
    private Stage loginStage;
    private TileCellModel tileCellModel;
    private KPIcollectionModel kpIcollectionModel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.userModel = new UserModel();
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            System.exit(0);
        }
        try {
            this.templateModel = new TemplateModel();
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            System.exit(0);
        }
        this.tileCellModel = new TileCellModel();
        try {
            this.kpIcollectionModel = new KPIcollectionModel();
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            System.exit(0);
        }
        clearInfo();
        try {
            setupPictures();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SignInAction() {
        signInLogic();
    }

    private void openAdminDashboard(User user){

        Stage adminDashboard = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/adminDashboardView.fxml"));
        try {
            loader.load();


        } catch (IOException e) {
            e.printStackTrace();
        }
        ((AdminDashboardViewController)loader.getController()).setUser(user);
        ((AdminDashboardViewController)loader.getController()).setUserModel(userModel);
        ((AdminDashboardViewController)loader.getController()).setTemplateModel(templateModel);
        ((AdminDashboardViewController)loader.getController()).setLoginStage(loginStage);
        ((AdminDashboardViewController)loader.getController()).setTileCellModel(tileCellModel);
        ((AdminDashboardViewController)loader.getController()).setKPIcollectionModel(kpIcollectionModel);
        Parent root = loader.getRoot();
        adminDashboard.setTitle("Admin Dashboard");
        adminDashboard.setScene(new Scene(root));
        adminDashboard.setResizable(true);
        //adminDashboard.setMaximized(true);
        //adminDashboard.initStyle(StageStyle.UNDECORATED);
        adminDashboard.show();
    }

    private void openKPIDashboard(User user){
        Stage KPI_Dashboard = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KPI_DashboardView.fxml"));

        try {
            loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        ((KPI_DashboardViewController)loader.getController()).setKPICollectionModel(kpIcollectionModel);
        ((KPI_DashboardViewController)loader.getController()).setKPICellModel(tileCellModel);
        ((KPI_DashboardViewController)loader.getController()).setTemplateModel(templateModel);
        ((KPI_DashboardViewController)loader.getController()).setUser(user);
        ((KPI_DashboardViewController)loader.getController()).setLoginStage(loginStage);
        KPI_Dashboard.setTitle(user.getUsername()+"  Dashboard");
        KPI_Dashboard.setScene(new Scene(root));
        KPI_Dashboard.setResizable(true);
        //KPI_Dashboard.setMaximized(true);
        //KPI_Dashboard.initStyle(StageStyle.UNDECORATED);
        KPI_Dashboard.show();
    }

    public void setupPictures() throws FileNotFoundException {
        InputStream streamA = new FileInputStream("src/main/resources/Files/1.jpg");
        Image imageA = new Image(streamA);
        cowView.setImage(imageA);
        cowView.fitWidthProperty().bind(cowPane.widthProperty());
        cowView.fitHeightProperty().bind(cowPane.heightProperty());

        InputStream streamB = new FileInputStream("src/main/resources/Files/2.png");
        Image imageB = new Image(streamB);
        arlaLogoView.setImage(imageB);
        arlaLogoView.fitWidthProperty().bind(logoPane.widthProperty());
        arlaLogoView.fitHeightProperty().bind(logoPane.heightProperty());
    }

    @FXML
    private void closeAction() {
        loginStage = (Stage) signInButton.getScene().getWindow();
        loginStage.hide();
    }

    @FXML
    private void closeAppAction() {
        Platform.exit();
    }

    public void signInLogic(){
        List<User> listusers = userModel.getListUsers();
        String username = usernameField.getText().strip();
        String password = passwordField.getText().strip();

        if(username.isEmpty() || password.isEmpty() ){
            loginErrorInfo();
        }
        HandlingPassword passwordHandler = new HandlingPassword();
        String passwordHash = passwordHandler.returnHash(password);

        for (User user: listusers) {
            if(user.getUsername().equals(username) && user.getPasswordHash().equals(passwordHash)){
                clearInfo();
                closeAction();
                if(user.getAdmin()) {
                    openAdminDashboard(user);
                    clearInfo();
                    break;
                }
                else
                    openKPIDashboard(user);
                    clearInfo();
                    break;
            }else {
                loginErrorInfo();
            }
        }
    }

    public void clearInfo(){
        usernameField.clear();
        passwordField.clear();
        usernameField.setUnFocusColor(Color.BLACK);
        passwordField.setUnFocusColor(Color.BLACK);
        infoLabel.setText("");
    }

    public void loginErrorInfo(){
        usernameField.setUnFocusColor(Color.RED);
        passwordField.setUnFocusColor(Color.RED);
        usernameField.clear();
        passwordField.clear();
        infoLabel.setText("Incorrect credentials, try again?");
        infoLabel.setTextFill(Paint.valueOf("red"));
    }
}
