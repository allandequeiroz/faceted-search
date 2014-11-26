package co.mutt.fun.repository;

import co.mutt.fun.criteria.FacetCriteria;
import co.mutt.fun.indexer.MassIndexer;
import co.mutt.fun.infra.HibernateUtil;
import co.mutt.fun.model.Company;
import co.mutt.fun.model.State;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.facet.Facet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class IndexedContentTest {

    private Session session;
    private FullTextSession fullTextSession;
    private IndexedContent<Company> indexedContent;

    @Before
    public void setUp() throws Exception {
        this.session = HibernateUtil.openSession();
        this.fullTextSession = HibernateUtil.openFullTextSession(session);
        this.indexedContent = new IndexedContent<>(fullTextSession);
        List<Class> entidades = Arrays.asList(Company.class, State.class);
        new MassIndexer().init(fullTextSession,entidades);
    }

    @Test
    public void companiesByState(){
        Sort sort = new Sort(new SortField("name", SortField.STRING, true));
        String[] fields = {"states.acronym"};

        FacetCriteria criteria = giveMeACriteriaForCompany(fields, "SP", sort, 1, "nameFacet");
        FullTextQuery hibernateQuery = null;
        try {
            hibernateQuery = indexedContent.getDiscreteQuery(criteria);
        } catch (Exception e) {
            Assert.fail();
        }

        List<Facet> statesFacet = hibernateQuery.getFacetManager().getFacets("facetedSearch");
        Assert.assertTrue(statesFacet.size()==2);
    }

    @Test
    public void companiesByName(){
        Sort sort = new Sort(new SortField("name", SortField.STRING, true));
        String[] fields = {"name"};

        FacetCriteria criteria = giveMeACriteriaForCompany(fields, "mutt", sort, 1, "nameFacet");
        FullTextQuery hibernateQuery = null;
        try {
            hibernateQuery = indexedContent.getDiscreteQuery(criteria);
        } catch (Exception e) {
            Assert.fail();
        }

        List<Facet> statesFacet = hibernateQuery.getFacetManager().getFacets("facetedSearch");
        Assert.assertTrue(statesFacet.size()==1);
    }

    private FacetCriteria giveMeACriteriaForCompany(String[] fields, String term, Sort sort, Integer page, String facedField) {
        FacetCriteria criteria = null;
        try {
            criteria = indexedContent.getFacetCriteria();
        } catch (Exception e) {
            Assert.fail();
        }
        criteria.setPage(page);
        criteria.setFields(fields);
        criteria.setTerm(term);
        criteria.setClazz(Company.class);
        criteria.setFacedName("facetedSearch");
        criteria.setFacedField(facedField);
        criteria.setSort(sort);
        criteria.setPageSize(10);
        return criteria;
    }

}