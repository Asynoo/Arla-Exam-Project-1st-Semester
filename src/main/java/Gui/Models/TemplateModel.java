package Gui.Models;


import Be.Template;
import Bll.TemplateManager;
import Exceptions.DatabaseException;

import java.util.List;

/**
 * This model is responsible for communicating template data with the database, connecting files and locations.
 * */

public class TemplateModel {

    private List<Template> listTemplates;
    private TemplateManager templateManager;

    public TemplateModel() throws DatabaseException {
        this.templateManager = new TemplateManager();
        refreshTemplates();
    }

    public void refreshTemplates() throws DatabaseException {
        listTemplates = templateManager.getTemplateList();
    }

    public List<Template> getListTemplates() {
        return listTemplates;
    }

    public void createNewTemplate(Template newTemplate) throws DatabaseException {
        templateManager.createNewTemplate(newTemplate);
        refreshTemplates();
    }

    public void deleteTemplate(Template template) throws DatabaseException {
        templateManager.deleteTemplate(template);
        refreshTemplates();
    }
}
