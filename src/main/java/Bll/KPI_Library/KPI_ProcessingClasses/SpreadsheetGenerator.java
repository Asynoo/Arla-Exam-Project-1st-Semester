package Bll.KPI_Library.KPI_ProcessingClasses;

import com.gembox.spreadsheet.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SpreadsheetGenerator implements IViewGenerator{

    public TableView tableView = new TableView();

    @FXML
    private Pane excelPane;

    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    public  SpreadsheetGenerator(){}

    public Region generateView(String name)  {
        ExcelFile workbook = null;
        try {
            workbook = ExcelFile.load(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        String[][] sourceData = new String[100][26];
        for (int row = 0; row < sourceData.length; row++) {
            for (int column = 0; column < sourceData[row].length; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    sourceData[row][column] = cell.getValue().toString();
            }
        }
        Double magicNumberThatMakesTableViewScaleProperly = 1000.0;
        tableView.setPrefHeight(magicNumberThatMakesTableViewScaleProperly);
        return fillTable(sourceData);
    }

    private TableView fillTable(String[][] dataSource) {
        tableView.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row : dataSource)
            data.add(FXCollections.observableArrayList(row));
        tableView.setItems(data);

        for (int i = 0; i < dataSource[0].length; i++) {
            final int currentColumn = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(ExcelColumnCollection.columnIndexToName(currentColumn));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(currentColumn)));
            column.setEditable(true);
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setOnEditCommit(
                    (TableColumn.CellEditEvent<ObservableList<String>, String> t) -> {
                        t.getTableView().getItems().get(t.getTablePosition().getRow()).set(t.getTablePosition().getColumn(), t.getNewValue());
                    });
            tableView.getColumns().add(column);
        }
        return tableView;
    }
}
