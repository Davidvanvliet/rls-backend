package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class ExceptionalGaugingIdent {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
    private int imPartner;
    @ManyToOne
    private ExceptionalGaugingCode exceptionalGaugingCode;
}
