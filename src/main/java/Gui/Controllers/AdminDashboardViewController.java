package Gui.Controllers;

import Be.Template;
import Be.User;
import Bll.DashboardFileManager;
import Bll.HandlingPassword;
import Exceptions.DatabaseException;
import Gui.Models.KPIcollectionModel;
import Gui.Models.TemplateModel;
import Gui.Models.TileCellModel;
import Gui.Models.UserModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboardViewController implements Initializable {

    @FXML
    private ScrollPane usersScrollPane,templatesScrollPane;
    @FXML
    private VBox createUserBox,editUserBox, usersVBox, usersClickVBox, templatesClickVBox;
    @FXML
    private JFXPasswordField createPasswordField,editPasswordField;
    @FXML
    private JFXTextField createFirstNameField,createLastNameField,createUsernameField,editLastNameField,editFirstNameField,editUsernameField;
    @FXML
    private JFXCheckBox createIsAdminCheckbox,editIsAdminCheckbox;
    @FXML
    private StackPane dashboardStackPane;
    @FXML
    private BorderPane usersTab,templatesTab;
    @FXML
    private Label infoLabel, templatesLbl;
    @FXML
    private TilePane templatesTilePane;
    @FXML
    private JFXButton signOutButton,usersBtn,templatesBtn,newUserButton,arlaLogoButton;

    private UserModel userModel;
    private TemplateModel templateModel;
    private User selectedUser;
    private Stage loginStage;
    private DashboardEditorViewController editorController;
    private TileCellModel tileCellModel;
    private KPIcollectionModel kpIcollectionModel;
    private User userUnderEditing = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardStackPane.getChildren().forEach(node -> node.setVisible(false));
        createUserBox.setVisible(false);
        arlaLogoButton.setStyle("-fx-background-color:  #f3f2f1;-fx-background-radius: 0");
        templatesClickVBox.setVisible(true);
        templatesClickVBox.setManaged(true);
        usersClickVBox.setVisible(true);
        usersClickVBox.setManaged(true);

    }

    public void setTileCellModel(TileCellModel tileCellModel) {
        this.tileCellModel = tileCellModel;
    }


    public void newUserAction() {
        usersAction();
        createUserBox.setVisible(true);
        usersScrollPane.setVisible(false);
        editUserBox.setVisible(false);
        newUserButton.setStyle("-fx-background-color: white;-fx-background-radius: 0");
    }

    public void removeUser(User user){
        try {
            userModel.deleteUser(user);
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        dashboardStackPane.getChildren().forEach(node -> node.setVisible(false));
        showUserTiles();
        usersTab.setVisible(true);
        createUserBox.setVisible(false);
        editUserBox.setVisible(false);
        usersBtn.setStyle("-fx-background-color: white;-fx-background-radius: 0");
        templatesBtn.setStyle("-fx-background-color: #f3f2f1;-fx-background-radius: 0");
        hideUserClickVBoxBackground();
        templatesLbl.setVisible(false);

    }

    public void editUser(User user){
        userUnderEditing = user;
        editUserBox.setVisible(true);
        createUserBox.setVisible(false);
        usersScrollPane.setVisible(false);
        editUsernameField.setText(user.getUsername());
        editFirstNameField.setText(user.getFirstName());
        editLastNameField.setText(user.getLastName());
        editIsAdminCheckbox.setSelected(user.getAdmin());
    }

    public void saveEditUser() {
        String updatedUsername = editUsernameField.getText().strip();
        String updatedPassword = editPasswordField.getText();
        String updatedFirstName = editFirstNameField.getText();
        String updatedLastName = editLastNameField.getText();
        Boolean updatedIsAdminVal = editIsAdminCheckbox.isSelected();
        HandlingPassword password = new HandlingPassword();
        User updatedUser = new User(null, updatedUsername,password.returnHash(updatedPassword),updatedFirstName, updatedLastName, updatedIsAdminVal ,null);
        try {
            userModel.createNewUser(updatedUser);
            userModel.deleteUser(userUnderEditing);
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        showUserTiles();
    }

    @FXML
    private void newTemplateAction() {
        openTemplateEditor();
    }

    public void openTemplateEditor(){
        Stage dashBoardEditor = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/dashboardEditorView.fxml"));
        try {
            loader.load();
            editorController = loader.getController();
            editorController.setAdminController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        editorController.setTileCellModel(tileCellModel);
        editorController.setKPIcollectionModel(kpIcollectionModel);
        Parent root = loader.getRoot();
        dashBoardEditor.setScene(new Scene(root));
        dashBoardEditor.setResizable(true);
        dashBoardEditor.show();
    }

    public void setEditorTemplate(Template template){
        editorController.setTemplate(template);
    }

    public void addTemplate(Template template){
        if(!templateModel.getListTemplates().contains(template)) {
            try {
                templateModel.createNewTemplate(template);
            } catch (DatabaseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
            showTemplates();
        }
    }

    public void removeTemplate(Template template) {
        for (User user : userModel.getListUsers()) {
            if(user.getTemplateID().equals(template.getId())){
                user.setTemplateID(null);
                try {
                    userModel.updateUser(user);
                } catch (DatabaseException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
        try {
            templateModel.deleteTemplate(template);
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        DashboardFileManager.getInstance().removeFile(template);
        showTemplates();
    }

    @FXML
    private void usersAction() {
        dashboardStackPane.getChildren().forEach(node -> node.setVisible(false));
        showUserTiles();
        usersTab.setVisible(true);
        createUserBox.setVisible(false);
        editUserBox.setVisible(false);
        usersBtn.setStyle("-fx-background-color: white;-fx-background-radius: 0");
        templatesBtn.setStyle("-fx-background-color: #f3f2f1;-fx-background-radius: 0");
        hideUserClickVBoxBackground();
        templatesLbl.setVisible(false);
    }

    private void hideUserClickVBoxBackground(){
        newUserButton.setStyle("-fx-background-color: #f3f2f1;-fx-background-radius: 0");
    }

    @FXML
    private void templatesAction() {
        dashboardStackPane.getChildren().forEach(node -> node.setVisible(false));
        templatesTab.setVisible(true);
        templatesBtn.setStyle("-fx-background-color: white;-fx-background-radius: 0");
        usersBtn.setStyle("-fx-background-color: #f3f2f1;-fx-background-radius: 0");
        templatesLbl.setVisible(true);
        showTemplates();
    }

    @FXML
    private void logOutAction() {
        Stage KPI_DashboardStage = (Stage) signOutButton.getScene().getWindow();
        KPI_DashboardStage.close();
        loginStage.show();
    }

    @FXML
    private void arlaButtonAction(){
        arlaLogoButton.setStyle("-fx-background-color:  #f3f2f1;-fx-background-radius: 0");
        dashboardStackPane.getChildren().forEach(node -> node.setVisible(false));
        createUserBox.setVisible(false);
        templatesLbl.setVisible(false);
    }

    public void setUser(User user) {
        infoLabel.setText("Arla Foods | Admin KPI Control: " + user.getFirstName() + " " + user.getLastName());
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setTemplateModel(TemplateModel templateModel) {
        this.templateModel = templateModel;
    }

    public void setKPIcollectionModel(KPIcollectionModel kpIcollectionModel) {this.kpIcollectionModel = kpIcollectionModel; }

    public void setLoginStage(Stage logStage) {
        loginStage = logStage;
    }

    public void createUser() {
        String username = createUsernameField.getText();
        String password = createPasswordField.getText();
        String firstName = createFirstNameField.getText();
        String lastName = createLastNameField.getText();
        Boolean isAdmin = createIsAdminCheckbox.isSelected();

        if(createUsernameField.getText().isEmpty()||createPasswordField.getText().isEmpty()){
            createUsernameField.setUnFocusColor(Color.RED);
            createPasswordField.setUnFocusColor(Color.RED);
        }
        else
        {
            HandlingPassword passwordHandler = new HandlingPassword();
            String passwordHash = passwordHandler.returnHash(password);
            User newUser = new User(null, username, passwordHash, firstName, lastName, isAdmin,null);
            try {
                userModel.createNewUser(newUser);
            } catch (DatabaseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }

            createUsernameField.clear();
            createFirstNameField.clear();
            createIsAdminCheckbox.setSelected(false);
            createPasswordField.clear();
            createLastNameField.clear();

            createUserBox.setVisible(false);
            showUserTiles();
        }
    }

    private void showUserTiles() {
        editUserBox.setVisible(false);
        usersScrollPane.setVisible(true);
        List<User> usersList = userModel.getListUsers();
        usersVBox.getChildren().clear();
        for (User u : usersList) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Tiles/UserTile.fxml"));

            VBox vBox = null;
            try {
                vBox = loader.load();
                ((UserTileController) loader.getController()).setUser(u);
                ((UserTileController) loader.getController()).setTemplateModel(templateModel);
                ((UserTileController) loader.getController()).setUserModel(userModel);
                ((UserTileController) loader.getController()).setAdminController(this);
                if (u == selectedUser) vBox.setStyle("-fx-background-color:  #96E8BC;-fx-background-radius: 5");
            } catch (IOException e) {
                e.printStackTrace();
            }
            usersVBox.getChildren().add(vBox);
        }
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
        showUserTiles(); // inefficient, but not visible
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void cancelCreateUser() {
        createUsernameField.clear();
        createFirstNameField.clear();
        createIsAdminCheckbox.setSelected(false);
        createPasswordField.clear();
        createLastNameField.clear();
        createUserBox.setVisible(false);
        usersScrollPane.setVisible(true);
    }

    private void showTemplates() {
        templatesScrollPane.setVisible(true);
        List<Template> templateList = templateModel.getListTemplates();
        templatesTilePane.getChildren().clear();
        for (Template t : templateList) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Tiles/TemplateTile.fxml"));

            Pane pane = null;
            try {
                pane = loader.load();
                ((TemplateTileController) loader.getController()).setTemplate(t);
                ((TemplateTileController) loader.getController()).setAdminController(this);
                ((TemplateTileController) loader.getController()).setKPICellModel(tileCellModel);
                ((TemplateTileController) loader.getController()).setKPIcollectionModel(kpIcollectionModel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            templatesTilePane.getChildren().add(pane);
        }
    }
}

