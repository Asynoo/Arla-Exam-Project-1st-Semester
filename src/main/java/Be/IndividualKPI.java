package Be;

public class IndividualKPI {

    /**
     * A business entity containing information about individual kpis, location type and name
     * */

    private Integer id;
    private String name;
    private String type;

    private int typeID;
    private String source;

    public IndividualKPI(Integer id, String name, String type, String source) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.source = source;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    @Override
    public String toString() {
        return  name;
    }
}
