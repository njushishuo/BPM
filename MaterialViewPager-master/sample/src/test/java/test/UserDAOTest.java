package test;

import com.github.florent37.materialviewpager.sample.model.User;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;
import com.github.florent37.materialviewpager.sample.rest.UserDAO;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

    public static UserDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = DAOFactory.getUserDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544614750296"));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        User user = new User();
        user.setUsername("Jack Hill");
        user.setPassword("/* encrypted password */");
        user.setNickname("Hiller");
        user.setCompany("Baidu");
        user.setCity("Beijing");
        System.out.println(dao.postObject(user));
    }
}