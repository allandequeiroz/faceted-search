package co.mutt.fun.model;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Criado por @author <a href="mailto:allan@reclameaqui.com.br">Allan de Queiroz</a> em 21/02/2014.
 */
@Indexed
@Entity
public @Data class State implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Field(analyze = Analyze.NO, store = Store.YES)
    @NotFound(action = NotFoundAction.IGNORE)
    private String name;

    @Field(analyze = Analyze.NO, store = Store.YES)
    @Column(columnDefinition = "CHAR(2)")
    private String acronym;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(mappedBy = "states")
    @IndexedEmbedded(depth = 1, includePaths = {"name", "nameFacet"})
    private Set<Company> companies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (!acronym.equals(state.acronym)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return acronym.hashCode();
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                '}';
    }
}
