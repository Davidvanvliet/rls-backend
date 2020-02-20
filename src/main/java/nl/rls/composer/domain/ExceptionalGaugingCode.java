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
 * Acceptance agreement number, coded in UIC  404-2 chapter 4.9.2
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ExceptionalGaugingCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String value;
    private String description;

}
