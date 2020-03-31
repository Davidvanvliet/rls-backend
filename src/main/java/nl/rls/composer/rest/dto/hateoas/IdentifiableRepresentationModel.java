package nl.rls.composer.rest.dto.hateoas;

import org.springframework.hateoas.RepresentationModel;

public class IdentifiableRepresentationModel<T extends IdentifiableRepresentationModel<? extends T>> extends RepresentationModel<T> {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
