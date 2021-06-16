package Gui.Controllers;

import Be.Template;
import Be.User;
import Exceptions.DatabaseException;
import Gui.Models.TemplateModel;
import Gui.Models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class UserTileController{
    @FXML
    private HBox userChoicesHbox;
    @FXML
    private ChoiceBox<Template> templateChoiceBox;
    @FXML
    private VBox baseBox;
    @FXML
    private Button userEditButton;
    @FXML
    private Button removeUserButton;
    @FXML
    private Label adminLabelTile;
    @FXML
    private Label fullNameLabel,usernameLbl;

    private User user;

    private AdminDashboardViewController adminController;
    private UserModel userModel;

    public void setUser(User user){
        if (user.getAdmin()){
            adminLabelTile.setVisible(true);
            baseBox.setStyle("-fx-background-color:  #00800f;-fx-background-radius: 0");
            templateChoiceBox.setVisible(false);
        }
        this.user = user;
        fullNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        usernameLbl.setText(user.getUsername());
        setEditDeleteButtons();
    }

    public void setAdminController(AdminDashboardViewController adminDashboardViewController) {
        adminController = adminDashboardViewController;

    }
    public void setTemplateModel(TemplateModel templateModel) {
        List<Template> templates = templateModel.getListTemplates();
        for (Template template : templates) {
            templateChoiceBox.getItems().add(template);
            if(template.getId().equals(user.getTemplateID()))
                templateChoiceBox.setValue(template);
        }
        templateChoiceBox.setOnAction(event -> {
            if(!(templateChoiceBox.getValue()).getId().equals(user.getTemplateID())){
                user.setTemplateID((templateChoiceBox.getValue()).getId());
                try {
                    userModel.updateUser(user);
                } catch (DatabaseException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
    }
    public void setUserModel(UserModel userModel){
        this.userModel = userModel;
    }

    public void setEditDeleteButtons(){
        userChoicesHbox.setVisible(false);
        userEditButton.setOnAction((e) -> adminController.editUser(user));
        removeUserButton.setOnAction((e) -> adminController.removeUser(user));
    }

    public void selectUserAction() {
        if(adminController.getSelectedUser() != user) {
            adminController.setSelectedUser(user);
        }
        else {
            adminController.setSelectedUser(null);
        }
    }

    public void actionOnHover() {
        userChoicesHbox.setVisible(true);
        baseBox.setStyle("-fx-background-color:   #f1c12d;");
        fullNameLabel.setTextFill(Color.web("#000000", 0.8));
        usernameLbl.setTextFill(Color.web("#000000", 0.8));
        adminLabelTile.setTextFill(Color.web("#000000", 0.8));


    }

    public void notHovering() {
        userChoicesHbox.setVisible(false);
        if (user.getAdmin()){
            adminLabelTile.setVisible(true);
            baseBox.setStyle("-fx-background-color:  #00800f;-fx-background-radius: 0");
            adminLabelTile.setTextFill(Color.web("#ffffff", 0.8));
        } else {
            baseBox.setStyle("-fx-background-color:  #00aa14;");
            fullNameLabel.setTextFill(Color.web("#ffffff", 0.8));
            usernameLbl.setTextFill(Color.web("#ffffff", 0.8));
        }
    }
}
