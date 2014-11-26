package co.mutt.fun;

import co.mutt.fun.indexer.MassIndexer;
import co.mutt.fun.infra.HibernateUtil;
import co.mutt.fun.infra.Resources;
import co.mutt.fun.model.Company;
import co.mutt.fun.model.State;
import com.google.common.base.Stopwatch;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String...args){
        Stopwatch stopwatch = Stopwatch.createStarted();

        Session session = HibernateUtil.openSession();
        FullTextSession textSession = HibernateUtil.openFullTextSession(session);
        List<Class> entidades = Arrays.asList(Company.class, State.class);
        try {
            new MassIndexer().init(textSession,entidades);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        stopwatch.stop();

        long min = stopwatch.elapsed(TimeUnit.MINUTES);
        logger.info(Resources.getMessage("elapased.time",min));

        System.exit(0);
    }
}
