package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.BmtChangCi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BmtChangCi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BmtChangCiRepository extends JpaRepository<BmtChangCi, Long> {

}
