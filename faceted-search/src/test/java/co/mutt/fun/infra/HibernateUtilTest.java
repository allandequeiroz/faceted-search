package co.mutt.fun.infra;

import co.mutt.fun.model.Company;
import co.mutt.fun.model.State;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HibernateUtilTest {

    private Session session;
    private FullTextSession fullTextSession;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        this.session = HibernateUtil.openSession();
        this.fullTextSession = HibernateUtil.openFullTextSession(session);
        this.connection = HibernateUtil.getConnection(session);
    }

    @Test
    public void testOpenSession() throws Exception {
        List<Company> companies = session.createCriteria(Company.class).list();
        Assert.assertTrue(!companies.isEmpty());
    }

    @Test
    public void testOpenFullTextSession() throws Exception {
        List<State> states = fullTextSession.createCriteria(State.class).list();
        Assert.assertTrue(!states.isEmpty());
    }

    @Test
    public void testGetConnection() throws Exception {
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT * FROM Company_State";
        try {
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();
            Assert.assertTrue(rs.next());
        } catch (SQLException e) {
            Assert.fail();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}