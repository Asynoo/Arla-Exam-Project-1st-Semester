package Bll.KPI_Library;

import Be.IndividualKPI;
import Be.KPIType;
import Dal.KPIcollectionDao;
import Dal.KPIcollectionDaoDB;
import Exceptions.DatabaseException;

import java.util.List;

public class KPI_Library_Manager {

    KPIcollectionDao kpiDao;

    public KPI_Library_Manager(){
        this.kpiDao = new KPIcollectionDaoDB();
    }
    public List<IndividualKPI> getKPIcollection() throws DatabaseException {return kpiDao.getKPIcollection();}
    public List<KPIType> getKPITypeList() throws DatabaseException {
        return kpiDao.getKPITypeList();
    }

    public String saveNewKPITool (IndividualKPI newKPI) throws DatabaseException {
        if(newKPI.getName()==null||newKPI.getType()==null||newKPI.getSource()==null){
            String failed ="Illegal kpi provided!";
            System.out.println(failed);
            return failed;
        }else{
            kpiDao.saveNewKpiTool(newKPI);
            String success ="KPI is successfully sent to deletion!";
            return success;
        }
    }

    public String deleteKPITool(IndividualKPI kpi) throws DatabaseException {
        if(kpi.getId()==null){
            String failed = "Missing valid KPI_id, not possible to delete such a KPI!";
            return failed;
        }
        kpiDao.deleteKPITool(kpi);
        String success = "Successfully sent to deletion!";
        return success;
    }

}
