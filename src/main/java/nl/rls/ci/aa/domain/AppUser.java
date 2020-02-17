package nl.rls.ci.aa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    //	@Column(columnDefinition="tinyint(1) default 1")
    private boolean enabled;
//	@Column(columnDefinition="tinyint(1) default 0")
//	private boolean tokenExpired;

    @ManyToOne
    private Role role;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public AppUser(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = username;
    }

    @Override
    public String toString() {
        return "AppUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
                + ", password=" + password + ", enabled=" + enabled + ", role=" + role + ", owner=" + owner + "]";
    }


}