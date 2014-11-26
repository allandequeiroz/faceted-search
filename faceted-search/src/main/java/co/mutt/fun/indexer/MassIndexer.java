package co.mutt.fun.indexer;


import co.mutt.fun.infra.Resources;
import org.hibernate.CacheMode;
import org.hibernate.search.FullTextSession;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public class MassIndexer {

    private static final Logger logger = Logger.getLogger(MassIndexer.class.getName());

    public void init(FullTextSession fullTextSession, List<Class> entidades) throws InterruptedException {
        for(Class entidade : entidades){
            massiveIndexer(entidade, fullTextSession);
            fullTextSession.flush();
        }
    }

    private void massiveIndexer(Class clazz, FullTextSession fullTextSession) throws InterruptedException {
        logger.info(Resources.getMessage("indexing.class",clazz.getSimpleName()));

        org.hibernate.search.MassIndexer indexer = fullTextSession
                .createIndexer(clazz)
                .batchSizeToLoadObjects(25)
                .cacheMode(CacheMode.NORMAL)
                .threadsToLoadObjects(5)
                .idFetchSize(Integer.MIN_VALUE)
                .threadsForSubsequentFetching(20);
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,Resources.getMessage("indexing.error",e.getCause()));
            throw e;
        }
    }
}
