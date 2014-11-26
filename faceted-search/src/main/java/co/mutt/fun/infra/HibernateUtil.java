package co.mutt.fun.infra;


import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.jdbc.Work;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        return new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static Session openSession(){
        return HibernateUtil.getSessionFactory().openSession();
    }

    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static FullTextSession openFullTextSession(Session session){
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.setFlushMode(FlushMode.MANUAL);
        fullTextSession.setCacheMode(CacheMode.IGNORE);
        return fullTextSession;
    }

    public static Connection getConnection(Session session) {
        final Connection[] connection = new Connection[1];
        session.doWork(new Work() {
            public void execute(Connection con) throws SQLException {
                connection[0] = con;
            }

        });
        return connection[0];
    }

}
