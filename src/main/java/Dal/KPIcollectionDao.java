package Dal;

import Be.IndividualKPI;
import Be.KPIType;
import Exceptions.DatabaseException;

import java.util.List;

public interface KPIcollectionDao {
    List<IndividualKPI> getKPIcollection() throws DatabaseException;

    List<KPIType> getKPITypeList() throws DatabaseException;

    void saveNewKpiTool(IndividualKPI newKPI) throws DatabaseException;

    void deleteKPITool(IndividualKPI kpi) throws DatabaseException;
}
