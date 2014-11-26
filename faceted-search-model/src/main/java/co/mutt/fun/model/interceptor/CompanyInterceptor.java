package co.mutt.fun.model.interceptor;

import co.mutt.fun.model.Company;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
public class CompanyInterceptor implements EntityIndexingInterceptor<Company> {
    @Override
    public IndexingOverride onAdd(Company company) {
        if (company.getActive()==1) {
            return IndexingOverride.APPLY_DEFAULT;
        }
        return IndexingOverride.SKIP;
    }

    @Override
    public IndexingOverride onUpdate(Company company) {
        if (company.getActive()==1) {
            return IndexingOverride.UPDATE;
        } else {
            return IndexingOverride.REMOVE;
        }
    }

    @Override
    public IndexingOverride onDelete(Company company) {
        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onCollectionUpdate(Company company) {
        return onUpdate(company);
    }
}
