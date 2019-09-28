package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="DangerLabel")
@NoArgsConstructor
@Getter 
@Setter
public class DangerLabel {
/*
 * All Danger Label of this dangerous good according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).
 * 
1	Explosive materials, divisions 1.1, 1.2 and 1.3
1.4	Explosive materials, division 1.4
1.5	 Explosive materials, division 1.5
1.6	Explosive materials, division 1.6
2.1	Flammable gases
2.2	Non-flammable, non-toxic gases
2.3	Toxic gases	
3	 Flammable liquids
4.1	Flammable  solids , self-reactive substances and solid desensitized explosives
4.2	 Substances liable to spontaneous combustion
4.3	Substances which, in contact with water, emit flammable gases
5.1	Oxidizing substances
5.2	Organic peroxides
6.1	Toxic substances
6.2	Infectious substances
7A	Radioactive material, category I
7B	 Radioactive material, category II
7C	 Radioactive material, category III
7D	 (obsolete) should be used for general information about class 7
7E	 Fissile radioactive material
8	 Corrosive substances
9	  Miscellaneous dangerous substances and articles
 */
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	private String value;
	private String description;

		

}
