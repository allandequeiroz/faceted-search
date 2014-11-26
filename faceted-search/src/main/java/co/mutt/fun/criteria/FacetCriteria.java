package co.mutt.fun.criteria;

import lombok.Data;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.FlushMode;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.dsl.PhraseMatchingContext;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public @Data class FacetCriteria<T> {

    public static final int SLOP = 1000;
    public static final float THRESHOLD = 0.7F;

    protected Integer pageSize;
    protected Integer page;
    protected Class<T> clazz;
    protected String term;
    protected String[] fields;
    protected Sort sort;
    protected String facedName;
    protected String facedField;
    protected Integer maxResults;

    protected FullTextSession fullTextSession;
    protected FullTextQuery hibernateQuery;
    protected QueryBuilder queryBuilder;
    protected Query query;

    public FacetCriteria(FullTextSession fullTextSession)
    {
        this.fullTextSession = fullTextSession;
        this.fullTextSession.setFlushMode(FlushMode.MANUAL);
        this.fullTextSession.setDefaultReadOnly(true);
    }

    public FullTextQuery createDiscrete()
    {
        initializate();
        setBoundaries();
        setSort();
        setFaced();
        setSizeResults();
        return hibernateQuery;
    }

    protected void setFaced(){
        if(facedName != null && facedField != null)
        {
            FacetingRequest facetingRequest = queryBuilder
                .facet()
                .name(facedName)
                .onField(facedField)
                .discrete()
                .orderedBy(FacetSortOrder.COUNT_DESC)
                .includeZeroCounts(false)
                .createFacetingRequest();
            hibernateQuery.getFacetManager().enableFaceting(facetingRequest);
        }
    }

    protected void setSizeResults(){
        if(maxResults != null)
            hibernateQuery.setMaxResults(maxResults.intValue());
    }

    protected void setSort(){
        if(sort != null)
            hibernateQuery.setSort(sort);
    }

    protected void setBoundaries(){
        if(page != null){
            hibernateQuery.setFirstResult((page.intValue() - 1) * pageSize.intValue());
            hibernateQuery.setMaxResults(pageSize.intValue());
        }
    }

    protected void initializate(){
        queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(clazz).get();
        query = createQuery();
        hibernateQuery = fullTextSession.createFullTextQuery(query, new Class[] { clazz });
    }

    protected Query createQuery(){
        return queryBuilder.bool().should(createSentenceQuery(queryBuilder, term, SLOP)).should(createFuzzyQuery(queryBuilder)).createQuery();
    }

    protected Query createFuzzyQuery(QueryBuilder queryBuilder){
        return queryBuilder.keyword().fuzzy().withThreshold(THRESHOLD).onFields(fields).matching(term).createQuery();
    }

    protected Query createSentenceQuery(QueryBuilder queryBuilder, String searchTerm, Integer slop){
        PhraseMatchingContext phrase = queryBuilder.phrase().withSlop(slop).onField(fields[0]);

        if(fields.length > 1){
            for(int i = 1; i < fields.length; i++)
                phrase.andField(fields[i]);

        }
        return phrase.sentence(searchTerm).createQuery();
    }
}
