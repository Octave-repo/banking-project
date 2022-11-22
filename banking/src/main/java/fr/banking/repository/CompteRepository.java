package fr.banking.repository;

import fr.banking.entities.CarteEntity;
import fr.banking.entities.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<CompteEntity,String> {
}
