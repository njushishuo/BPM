package test;

import com.github.florent37.materialviewpager.sample.model.Label;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;
import com.github.florent37.materialviewpager.sample.rest.LabelDAO;

import org.junit.Before;
import org.junit.Test;

public class LabelDAOTest {

    public static LabelDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOFactory.getLabelDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544614143626"));
    }

    @Test
    public void putObject() {
        System.out.println("Put single object");
        Label label = dao.getObject("1544614143626");
        String name = "Data Structure";
        label.setName("Test_Labels");
        System.out.println(dao.putObject(label));
        label.setName(name);
        System.out.println(dao.putObject(label));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Label label = new Label();
        label.setName("FUCK");
        System.out.println(dao.postObject(label));
    }

    @Test
    public void deleteObject() {
        System.out.println("Delete Object");
        Label label = dao.getObject("1544664606026");
        System.out.println(dao.deleteObject(label));
    }
}