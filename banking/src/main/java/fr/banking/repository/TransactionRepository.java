package fr.banking.repository;

import fr.banking.entities.CarteEntity;
import fr.banking.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

}
