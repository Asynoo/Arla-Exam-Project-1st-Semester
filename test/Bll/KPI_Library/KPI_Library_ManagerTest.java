package Bll.KPI_Library;

import Be.IndividualKPI;
import Exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KPI_Library_ManagerTest {

    /**
     * Testing provided Individual KPIs fro saving or deletion. Depending on the data in the object it is either
     * accepted or declined fro those actions.
     *
     * While performing tests, there are thrown errors because of certain parts of app are missing in the testing
     * environment. Nonetheless tests are successful and and they also portray correct actions of our application.
     * */

    @Test
    void saveNewKPIToolFail_TypeNull() {
        KPI_Library_Manager kpiLibMan = new KPI_Library_Manager();
        IndividualKPI wrongKPI = new IndividualKPI(1,"LineChart",null, "test/source");
        String answer = null;
        try {
            answer = kpiLibMan.saveNewKPITool(wrongKPI);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"Illegal kpi provided!");
    }

    @Test
    void saveNewKPIToolFail_NameNull() {
        KPI_Library_Manager kpiLibMan = new KPI_Library_Manager();
        IndividualKPI wrongKPI = new IndividualKPI(1,null,"AREA_CHART", "test/source");
        String answer = null;
        try {
            answer = kpiLibMan.saveNewKPITool(wrongKPI);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"Illegal kpi provided!");
    }

    @Test
    void saveNewKPIToolFail_RecourseNull() {
        KPI_Library_Manager kpiLibMan = new KPI_Library_Manager();
        IndividualKPI wrongKPI = new IndividualKPI(1,"NameName","AREA_CHART", null);
        String answer = null;
        try {
            answer = kpiLibMan.saveNewKPITool(wrongKPI);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"Illegal kpi provided!");
    }

    @Test
    void deleteKPIToolFail() {
        KPI_Library_Manager kpiLibMan = new KPI_Library_Manager();
        IndividualKPI wrongKPI = new IndividualKPI(null,"LineChart","LIST_VIEW", "test/source");
        String answer = null;
        try {
            answer = kpiLibMan.deleteKPITool(wrongKPI);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"Missing valid KPI_id, not possible to delete such a KPI!");
    }

    @Test
    void deleteKPIToolSuccess() {
        KPI_Library_Manager kpiLibMan = new KPI_Library_Manager();
        IndividualKPI correctKPI = new IndividualKPI(10000000,"LineChart","LIST_VIEW", "test/source");
        String answer = null;
        try {
            answer = kpiLibMan.deleteKPITool(correctKPI);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"Successfully sent to deletion!");
    }
}