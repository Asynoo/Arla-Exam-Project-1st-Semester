package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.scene.chart.AreaChart;
import javafx.scene.layout.Region;

public interface IViewGenerator {
    Region generateView(String source);
}
