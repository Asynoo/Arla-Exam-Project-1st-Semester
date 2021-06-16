package Be;


/**
 *  A unit responsible for the information needed to build up the template in the view.
 * */
public class Template {
    String uri,name;
    Integer id;

    public Template(Integer id,String name, String uri){
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
