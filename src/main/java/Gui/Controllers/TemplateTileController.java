package Gui.Controllers;

import Be.Template;
import Gui.Models.KPIcollectionModel;
import Gui.Models.TileCellModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TemplateTileController {

    @FXML
    private Label nameLbl;

    private Template template;

    private AdminDashboardViewController adminController;
    private TileCellModel tileCellModel;
    private KPIcollectionModel kpIcollectionModel;

    public void setTemplate(Template template){
        this.template = template;
        nameLbl.setText(template.getName());
    }

    public void setAdminController(AdminDashboardViewController adminDashboardViewController) {
        adminController = adminDashboardViewController;
    }
    
    public void setKPICellModel(TileCellModel tileCellModel) {
        this.tileCellModel = tileCellModel;
    }

    public void setKPIcollectionModel(KPIcollectionModel kpIcollectionModel) {
        this.kpIcollectionModel = kpIcollectionModel;
    }

    @FXML
    private void previewTemplateAction() {
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
        ((KPI_DashboardViewController)loader.getController()).setTemplate(template);
        KPI_Dashboard.setTitle(template.getName() +" Preview Dashboard");
        KPI_Dashboard.setScene(new Scene(root));
        KPI_Dashboard.setResizable(true);
        KPI_Dashboard.setMaximized(true);
        KPI_Dashboard.initStyle(StageStyle.UNDECORATED);
        KPI_Dashboard.show();
    }

    @FXML
    private void editTemplateAction() {
        adminController.openTemplateEditor();
        adminController.setEditorTemplate(template);
    }

    @FXML
    private void deleteTemplateAction() {
        adminController.removeTemplate(template);
    }
}
