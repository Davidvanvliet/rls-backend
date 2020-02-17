package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author berend.wilkens
 * Additional codified information on the load. Coding Structures as defined in 404-2  chapter 4.1
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class InfoOnGoodsShapeTypeDanger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String value;
    private String description;
}
