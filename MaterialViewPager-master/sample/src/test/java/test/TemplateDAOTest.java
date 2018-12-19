package test;

import com.github.florent37.materialviewpager.sample.model.Label;
import com.github.florent37.materialviewpager.sample.model.Template;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;
import com.github.florent37.materialviewpager.sample.rest.TemplateDAO;
import com.github.florent37.materialviewpager.sample.util.StringUtil;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class TemplateDAOTest {

    public static TemplateDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOFactory.getTemplateDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        Template template = dao.getObject("1544618242313");
        System.out.println(template);
        Map<Label, Integer> map = StringUtil.parseTemplateItems(template.getItems());
        System.out.println(map);
        System.out.println(StringUtil.buildTemplateItems(map));
//        System.out.println(RestUtil.getRecruitInfo(template));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void putObject() {
        System.out.println("Put Object");
        Template template = dao.getObject("1544618242313");
        System.out.println(template);
        template.setItems("1544614143626,2;1544615626185,1");
        System.out.println(dao.putObject(template));
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Template template = new Template();
        template.setName("Java Template v1");
        template.setItems("1544618086251");
        template.setRecruitId("1544616954639");
        System.out.println(dao.postObject(template));
    }

    @Test
    public void getByRecruit() {
        System.out.println(dao.getTemplatesByRecruit("1544616954639"));
    }
}