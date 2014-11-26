package co.mutt.fun.indexer;

import co.mutt.fun.infra.HibernateUtil;
import co.mutt.fun.model.Company;
import co.mutt.fun.model.State;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MassIndexerTest  {

    @Test
    public void testInit() throws Exception {
        Session session = HibernateUtil.openSession();
        FullTextSession fullTextSession = HibernateUtil.openFullTextSession(session);
        List<Class> entidades = Arrays.asList(Company.class, State.class);
        new MassIndexer().init(fullTextSession,entidades);
        Assert.assertTrue(true);
    }

}