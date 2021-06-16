package Dal;

import Be.IndividualKPI;
import Be.KPIType;
import Exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KPIcollectionDaoDB implements KPIcollectionDao {

    private static DataAccess dataAccess = new DataAccess();

    @Override
    public List<IndividualKPI> getKPIcollection() throws DatabaseException {

        List<IndividualKPI> KPIcollection = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()){
            String sql = "SELECT KPI_Tools.KPIid,KPIname,KPIdataSource,KPI_type.KPI_type_name FROM KPI_Tools JOIN KPI_type ON KPI_Tools.KPItypeId=KPI_type.KPI_type_id";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("KPIid");
                String name = rs.getString("KPIname");
                String source = rs.getString("KPIdataSource");
                String type = rs.getString("KPI_type_name");
                IndividualKPI kpi = new IndividualKPI(id, name, type, source);
                KPIcollection.add(kpi);
            }
        }catch (Exception e){
            throw new DatabaseException("Couldn't fetch the KPIs from the database");
        }
        return KPIcollection;
    }

    @Override
    public List<KPIType> getKPITypeList() throws DatabaseException {
        List<KPIType> listOfKPITypes = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()){
            String sql = "SELECT * FROM KPI_Type";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("KPI_type_id");
                String name = rs.getString("KPI_type_name");
                KPIType kpiType = new KPIType(name, id);
                listOfKPITypes.add(kpiType);
            }

        }catch (Exception e){
            throw new DatabaseException("Couldn't fetch the KPIs from the database");
        }
        return listOfKPITypes;
    }

    @Override
    public void saveNewKpiTool(IndividualKPI newKPI) throws DatabaseException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "INSERT INTO KPI_Tools (KPIname,KPItypeId,KPIdataSource) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,newKPI.getName());
            statement.setInt(2,newKPI.getTypeID());
            statement.setString(3,newKPI.getSource());
            statement.executeUpdate();
        }catch (Exception e){
            throw new DatabaseException("Couldn't access the KPI table in the database");
        }
    }

    @Override
    public void deleteKPITool(IndividualKPI kpi) throws DatabaseException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "DELETE FROM KPI_Tools WHERE KPIid = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,kpi.getId());
            st.executeUpdate();
        }catch (Exception e){
            throw new DatabaseException("Couldn't access the KPI table in the database");
        }
    }

}
