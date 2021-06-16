package Bll.KPI_Library;

import Be.IndividualKPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KPI_PaneCreator_TestTest {

    /**
     * The purpose if this test is to determine whether or not the switch statment is working as it should.
     * KPI_Pane_Creator is a classed used to return the correct view (table view, line chart, pie chart, img, webView),
     * depending on the kpi provided.
     *
     * We had big trouble using actual class and therefore made the exact copy of functional class just without the
     * follow through with actually creating the nodes.
     *
     * Logic behind it is if the switch statment works correctly the view will be generated correctly too. And if the
     * test is passed but the node is not working - then the problem is in the node generator itself and that is the
     * Integration test and we haven't learned how to do them yet.
     * */

    @Test
    void createPaneLineChart() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","LINE_CHART", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneAreaChart() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","AREA_CHART", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneAreaChart1() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","AREA_CHART1", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneAreaOverlay() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","AREA_OVERLAY", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneStackedArea() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","STACKED_AREA", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPanePieChart() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","PIE_CHART", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneSpreadsheet() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","SPREADSHEET_KPI", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneImageKPI() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","IMAGE_KPI", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void createPaneWebPage() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","WEBPAGE_KPI", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }

    @Test
    void WrongInput_createPaneLineChart() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","LIINE_CHART", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertNotEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }



    @Test
    void WrongInput_createPaneAreaChart1() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","ARREA_CHART1", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertNotEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }



    @Test
    void WrongInput_createPaneStackedArea() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","STACKEDD_AREA", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertNotEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }



    @Test
    void WrongInput_createPaneSpreadsheet() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","SPREADDSHEET_KPI", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertNotEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }



    @Test
    void WrongInput_createPaneWebPage() {
        KPI_PaneCreator_Test kpiCreator = new KPI_PaneCreator_Test();
        IndividualKPI lineChartKPI = new IndividualKPI(1,"LineChart","WEBPAGE__KPI", "test/source");
        kpiCreator.createPane(lineChartKPI);
        Assertions.assertNotEquals(lineChartKPI.getType(),kpiCreator.caseUsed);
    }
}