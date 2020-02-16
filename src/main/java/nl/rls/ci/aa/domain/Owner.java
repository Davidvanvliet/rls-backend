package nl.rls.ci.aa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;
	private String name;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "owner_id")
	@OrderBy("lastName")
	private List<AppUser> users;
	@OneToMany(mappedBy="owner", cascade = CascadeType.ALL)
	private List<License> licenses = new ArrayList<>();
	
	public boolean isSubscribed() {
		
		return true;
	}
	
	@Override
	public String toString() {
		return "Owner [id=" + id + ", code=" + code + ", name=" + name + ", licenses=" + licenses + "]";
	}
	

}