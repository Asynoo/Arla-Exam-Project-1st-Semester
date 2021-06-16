package Be;

public class KPIType {

    /**
     *  Essential data unit for the Individual KPI as it holds the information about how the kpi should be processed
     *  into the view
     * */

    private String name;
    private int KPITypeId;

    public KPIType(String name, Integer id){
        this.name = name;
        this.KPITypeId = id;
    }

    public String getName() {return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getKPITypeId() {
        return KPITypeId;
    }

    public void setKPITypeId(int KPITypeId) {
        this.KPITypeId = KPITypeId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
