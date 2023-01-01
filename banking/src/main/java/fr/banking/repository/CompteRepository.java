package fr.banking.repository;

import fr.banking.entities.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<CompteEntity,String> {
    List<CompteEntity> findCompteEntityByClientsId(long id);

    CompteEntity findCompteEntityByiBAN(String iban);
}
