package Gui.Controllers;

import Be.Template;
import Be.User;
import Bll.DashboardFileManager;
import Exceptions.FileHandlingException;
import Gui.Models.TemplateModel;
import Gui.Models.TileCellModel;
import javafx.animation.Animation;
import Gui.Models.KPIcollectionModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class KPI_DashboardViewController implements Initializable {

    @FXML
    private StackPane previewPane;
    @FXML
    private Label time;
    private final Object clockObject = new Object();
    private int refreshTimer = 0;
    private Stage loginStage;
    private Template template;
    private FXMLLoader templateLoader;
    private TemplateModel templateModel;
    private KPIcollectionModel kpIcollectionModel;
    private TileCellModel tileCellModel;
    private final DashboardFileManager dashboardFileManager = DashboardFileManager.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clock();
    }


    @FXML
    private void returnAction() {
        Stage KPI_DashboardStage = (Stage)previewPane.getScene().getWindow();
        KPI_DashboardStage.close();
        if(loginStage != null){
            loginStage.show();
        }
    }

    public void setKPICollectionModel(KPIcollectionModel kpIcollectionModel) {
        this.kpIcollectionModel = kpIcollectionModel;
    }

    public void clock(){
        Thread clockKPIThread = new Thread(() -> {
            synchronized (clockObject) {
                Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    refreshTimer++;
                    LocalTime currentTime = LocalTime.now();
                    time.setText("   " + currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
                    if (refreshTimer == 1 && template != null) {
                        ((TemplateController) templateLoader.getController()).updateKpiSizes();

                    }
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                Platform.runLater(() -> clock.setCycleCount(Animation.INDEFINITE));
                Platform.runLater(clock::play);
            }
            });
        clockKPIThread.setDaemon(true);
        clockKPIThread.start();
    }

    public void setUser(User user) {
        for (Template template : templateModel.getListTemplates()) {
            if(template.getId().equals(user.getTemplateID())){
                setTemplate(template);
                break;
            }
        }
        if(template == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No dashboard assigned to this account\n please contact an admin", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setTemplateModel(TemplateModel templateModel) {
        this.templateModel = templateModel;
    }

    public void setTemplate(Template template) {
        this.template = template;
        previewPane.getChildren().clear();
        templateLoader = new FXMLLoader(this.getClass().getResource("/Dashboards/BaseTemplate.fxml"));
        StackPane templatePane = null;
        try {
            templatePane = templateLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((TemplateController)templateLoader.getController()).setKPICollectionModel(kpIcollectionModel);
        ((TemplateController)templateLoader.getController()).setKPItileModel(tileCellModel);

        previewPane.getChildren().add(templatePane);
        loadJson();

        time.setOnMouseClicked(mouseEvent -> ((TemplateController)templateLoader.getController()).updateKpiSizes());
    }

    public void setLoginStage(Stage logStage) {
        loginStage = logStage;
    }

    private void loadJson(){
        try {
            ((TemplateController)templateLoader.getController()).loadTemplateGrid(dashboardFileManager.loadFile(template),false);
        } catch (FileHandlingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            returnAction();
        }
    }

    public void setKPICellModel(TileCellModel tileCellModel) {
     this.tileCellModel = tileCellModel;
    }


}
