package test;

import com.github.florent37.materialviewpager.sample.model.Recruit;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;
import com.github.florent37.materialviewpager.sample.rest.RecruitDAO;
import com.github.florent37.materialviewpager.sample.util.RestUtil;

import org.junit.Before;
import org.junit.Test;

public class RecruitDAOTest {

    public static RecruitDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOFactory.getRecruitDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        Recruit recruit = dao.getObject("1544616954639");
        System.out.println(recruit);
        System.out.println(RestUtil.getOwnerInfo(recruit));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Recruit recruit = new Recruit();
        recruit.setName("Baidu Season 3 JAVA recruit");
        recruit.setDescription("Baidu Season 3 JAVA recruit - 211 & 985 needed");
        recruit.setType("WEB");
        recruit.setOwnerId("1544614750296");
        System.out.println(dao.postObject(recruit));
    }
}