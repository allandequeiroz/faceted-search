package co.mutt.fun.repository;

import co.mutt.fun.criteria.FacetCriteria;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public class IndexedContent<T> {

    private FullTextSession fullTextSession;

    public IndexedContent(EntityManager em){
        this.fullTextSession = Search.getFullTextSession(em.unwrap(Session.class));
    }

    public IndexedContent(Session session){
        this.fullTextSession = Search.getFullTextSession(session);
    }

    public IndexedContent(FullTextSession fullTextSession){
        this.fullTextSession = fullTextSession;
    }

    public T get(Class<T> clazz, Serializable id) throws Exception {
        try {
            return (T) fullTextSession.get(clazz, id);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public void update(T t, Serializable id, Class<T> clazz) throws Exception {
        try {
            fullTextSession.purge(clazz, id);
            fullTextSession.index(t);
            fullTextSession.flushToIndexes();
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public List<T> list(FacetCriteria criteria) throws Exception {
        try {
            return criteria.createDiscrete().list();
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public FullTextQuery getDiscreteQuery(FacetCriteria criteria) throws Exception {
        try {
            return criteria.createDiscrete();
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public FacetCriteria getFacetCriteria() throws Exception {
        try {
            return new FacetCriteria(fullTextSession);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public void delete(Serializable id, Class<T> clazz) throws Exception {
        try {
            fullTextSession.purge(clazz, id);
            fullTextSession.flushToIndexes();
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }
}
