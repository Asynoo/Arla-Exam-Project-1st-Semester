package Bll;


import Be.Template;
import Dal.TemplateDao;
import Dal.TemplateDaoDB;
import Exceptions.DatabaseException;

import java.util.List;

public class TemplateManager {

    TemplateDao templateDao;

    public TemplateManager(){
        this.templateDao = new TemplateDaoDB();
    }

    public List<Template> getTemplateList() throws DatabaseException {
        return templateDao.getTemplateList();
    }

    public void createNewTemplate(Template newTemplate) throws DatabaseException { templateDao.createNewTemplate(newTemplate);}

    public void deleteTemplate(Template template) throws DatabaseException {templateDao.deleteTemplate(template);}
}
