package Dal;


import Be.Template;
import Exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemplateDaoDB implements TemplateDao {

    private static final DataAccess dataAccess = new DataAccess();

    @Override
    public List<Template> getTemplateList() throws DatabaseException {
        ArrayList<Template> listOfTemplates = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()){
            String sql = "SELECT * FROM Templates";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                int templateId = rs.getInt("TemplateID");
                String name = rs.getString("Name");
                String uri = rs.getString("URI");
                Template template = new Template(templateId, name, uri);
                listOfTemplates.add(template);
            }

        }catch (Exception e){
            throw new DatabaseException("Couldn't fetch the Template table in the database");
        }
        return listOfTemplates;
    }

    @Override
    public void createNewTemplate(Template newTemplate) throws DatabaseException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "INSERT INTO Templates (Name,URI) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString (1, newTemplate.getName());
            statement.setString(2, newTemplate.getUri());
            statement.executeUpdate();

        }catch (Exception e){
            throw new DatabaseException("Couldn't access the Template table in the database");
        }
    }

    @Override
    public void deleteTemplate(Template template) throws DatabaseException {
        try (Connection con = dataAccess.getConnection()) {
            String sql1 = "DELETE FROM Templates WHERE templateID = ?";
            PreparedStatement statement1 = con.prepareStatement(sql1);
            statement1.setInt(1, template.getId());
            statement1.executeUpdate();
        } catch (Exception e){
            throw new DatabaseException("Couldn't access the Template table in the database");
        }
    }
}
