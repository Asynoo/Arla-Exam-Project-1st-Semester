package Gui.Controllers;

import Be.IndividualKPI;
import Exceptions.DatabaseException;
import Gui.Models.KPIcollectionModel;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Controller responsible for the deletion of the KPIs in the editDadhboardView.
 * This works by right-clicking the kpi after which the dialog pops up guiding user.
 * */

public class DeleteKPITileController {
    @FXML
    private JFXButton cancelButton;
    private IndividualKPI kpi;
    private KPIcollectionModel kpIcollectionModel;

    public void setKPI(IndividualKPI kpi){
        this.kpi = kpi;
    }

    public void deleteKPI() {
        try {
            kpIcollectionModel.deleteKPITool(kpi);
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        Stage st = (Stage)cancelButton.getScene().getWindow();
        st.close();
    }

    public void cancelDelete() {
        Stage st = (Stage)cancelButton.getScene().getWindow();
        st.close();
    }

    public void setKPIcollectionModel(KPIcollectionModel kpIcollectionModel) {
        this.kpIcollectionModel = kpIcollectionModel;
    }
}
