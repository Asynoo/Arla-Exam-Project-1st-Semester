package Bll;

import Be.Template;
import Be.TemplateGrid;
import Exceptions.FileHandlingException;
import com.google.gson.Gson;

import java.io.*;


public class DashboardFileManager {
    private Gson gson = new Gson();

    private static DashboardFileManager dashboardFileManager = new DashboardFileManager();

    private DashboardFileManager(){}

    public static DashboardFileManager getInstance(){
        return dashboardFileManager;
    }

    public void saveFile(Template template, TemplateGrid templateGrid) throws FileHandlingException {
        try (FileWriter writer = new FileWriter("src/main/resources/Dashboards" + template.getUri())) {
            gson.toJson(templateGrid, writer);
        } catch (IOException e) {
            throw new FileHandlingException("Couldn't write to the file selected");
        }
    }

    public TemplateGrid loadFile(Template template) throws FileHandlingException {
        TemplateGrid templateGrid;
        try {
            Reader reader = new FileReader("src/main/resources/Dashboards" + template.getUri());
            templateGrid = gson.fromJson(reader, TemplateGrid.class);
        } catch (FileNotFoundException e) {
            throw new FileHandlingException("Couldn't find the associated file");
        }
        return templateGrid;
    }

    public void removeFile(Template template){
        File templateFile = new File("src/main/resources/Dashboards" + template.getUri());
        templateFile.delete();
    }
}
