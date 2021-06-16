package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CSVgraphGenerator_AreaChart1 implements IViewGenerator {
    private AreaChart<Number, Number> graphType1;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public CSVgraphGenerator_AreaChart1(){}


    public Region generateView(String source){
        xAxis = new NumberAxis(1,31,1);
        yAxis = new NumberAxis();
        graphType1 = new AreaChart(xAxis, yAxis);

        getSeriesFromCSV(source);

        return graphType1;
    }



    public void getSeriesFromCSV(String source) {

        XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<Number, Number>();

        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(source));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(";");
                if(data[0].contains("Legend")){
                    //xAxis.setLabel(data[1]);
                    //yAxis.setLabel(data[2]);
                    System.out.println("Label names are set");
                }else {
                    Date providedDate = new SimpleDateFormat("dd.MM.yyyy").parse(data[0]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(providedDate);
                    int dayNumber = calendar.get(Calendar.DAY_OF_MONTH);
                    System.out.println(dayNumber);

                    int sumColumn1 = Integer.parseInt(data[1]);
                    int sumColumn2 = Integer.parseInt(data[2]);
                    int sumColumn3 = Integer.parseInt(data[3]);
                    int sumColumn4 = Integer.parseInt(data[4]);
                    int sumAll = sumColumn1+sumColumn2+sumColumn3+sumColumn4;

                    dataSeries1.getData().add(new XYChart.Data(dayNumber, sumAll));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        graphType1.getData().add(dataSeries1);
        graphType1.setLegendVisible(false);
    }
}
