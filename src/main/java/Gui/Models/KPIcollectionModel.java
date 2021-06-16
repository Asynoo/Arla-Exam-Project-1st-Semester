package Gui.Models;

import Be.IndividualKPI;
import Be.KPIType;
import Bll.KPI_Library.KPI_Library_Manager;
import Exceptions.DatabaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Following model is handling the IndividualKPIs and kpi types, and the crud functionality associated with them.
 * */

public class KPIcollectionModel {

    private List<IndividualKPI> KPIcollection;
    private List<KPIType> KPITypeCollection;
    private ObservableList<KPIType> observableListOfKPITypes = FXCollections.observableArrayList();

    private final KPI_Library_Manager KPImanager;

    public KPIcollectionModel() throws DatabaseException {
        this.KPImanager = new KPI_Library_Manager();
        refreshKPIList();
        refreshKPITypeList();
    }

    public List<IndividualKPI> getKPIcollection() throws DatabaseException {
        return KPIcollection;
    }

    public ObservableList<KPIType> getKPITypeList() throws DatabaseException {
        observableListOfKPITypes.addAll(KPITypeCollection);
        return observableListOfKPITypes;
    }

    public void refreshKPIList() throws DatabaseException {
        KPIcollection = KPImanager.getKPIcollection();
    }
    public void refreshKPITypeList() throws DatabaseException {
        KPITypeCollection = KPImanager.getKPITypeList();
    }

    public void saveNewKPITool(IndividualKPI newKPI) throws DatabaseException {
        KPImanager.saveNewKPITool(newKPI);
        refreshKPIList();
    }

    public void deleteKPITool(IndividualKPI kpi) throws DatabaseException {
        KPImanager.deleteKPITool(kpi);
        refreshKPIList();
    }
}
