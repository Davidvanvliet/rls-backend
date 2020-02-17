package nl.rls.ci.aa.repository;

import nl.rls.ci.aa.domain.License;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface LicenseRepository extends CrudRepository<License, Integer> {
    @Query("SELECT li FROM License li, Owner ow, AppUser us JOIN us.owner owner JOIN ow.licenses license WHERE us.email = ?1 and li.validFrom<= ?2 and li.validTo >= ?2")
    public List<License> validLicenseByUsername(String username, Date date);
}
