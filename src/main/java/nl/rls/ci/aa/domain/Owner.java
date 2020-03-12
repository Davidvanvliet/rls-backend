package nl.rls.ci.aa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<License> licenses = new ArrayList<>();

    public Owner(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public boolean isSubscribed() {

        return true;
    }

    @Override
    public String toString() {
        return "Owner [id=" + id + ", code=" + code + ", name=" + name + ", licenses=" + licenses + "]";
    }


}
