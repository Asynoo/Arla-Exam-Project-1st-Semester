package Be;

/**
 * Simplest unit of the template - Individual cell containing the kpi.
 * */

public class TemplateCell{
    private int x,y,width,height;
    private Integer kpiID;
    private boolean visible;

    public TemplateCell (int x,int y,int width,int height,boolean visible,Integer kpiID){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.kpiID = kpiID;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Integer getKpiID() {
        return kpiID;
    }
}
