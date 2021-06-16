package Be;

/**
 * This is the basic grid upon which the dashboard is built.
 * It kan be 10*10 or 100*100 as well as 46*78
 * But based on that number and the size of the screen cells will be calculated.
 * */

import java.util.List;

public class TemplateGrid {
    private double rows;
    private double columns;
    private List<TemplateCell> templateCells;

    public TemplateGrid(double rows, double columns, List<TemplateCell> templateCells) {
        this.rows = rows;
        this.columns = columns;
        this.templateCells = templateCells;
    }
    public TemplateGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public double getRows() {
        return rows;
    }

    public double getColumns() {
        return columns;
    }

    public List<TemplateCell> getTemplateCells() {
        return templateCells;
    }

    public void setTemplateCells(List<TemplateCell> templateCells) {
        this.templateCells = templateCells;
    }
}
