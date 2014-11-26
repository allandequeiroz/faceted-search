package co.mutt.fun.model;

import co.mutt.fun.model.interceptor.CompanyInterceptor;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by <a href="mailto:allandequeiroz@gmail.com">Allan de Queiroz</a>.
 */
@Indexed(interceptor = CompanyInterceptor.class)
@Entity
@Boost(2.0f)
public @Data class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NumericField
    @Field(store = Store.YES)
    private int id;

    @Field(store = Store.YES)
    private String name;

    @Field(analyze = Analyze.NO, store = Store.YES)
    @Column(name = "name", nullable = false, insertable = false, updatable = false)
    private String nameFacet;

    @Column
    private short active;

    @ManyToMany
    @NotFound(action = NotFoundAction.IGNORE)
    @IndexedEmbedded(depth = 1, includePaths = {"name", "acronym"})
    private Set<State> states;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        if (!name.equals(company.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                '}';
    }
}
