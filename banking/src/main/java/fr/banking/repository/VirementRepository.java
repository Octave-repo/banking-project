package fr.banking.repository;

import fr.banking.entities.TransactionEntity;
import fr.banking.entities.VirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirementRepository  extends JpaRepository<VirementEntity,Long>{
}
