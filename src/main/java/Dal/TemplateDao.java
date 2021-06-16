package Dal;

import Be.Template;
import Exceptions.DatabaseException;

import java.util.List;

public interface TemplateDao {
    List<Template> getTemplateList() throws DatabaseException;

    void createNewTemplate(Template newTemplate) throws DatabaseException;

    void deleteTemplate(Template template) throws DatabaseException;
}
