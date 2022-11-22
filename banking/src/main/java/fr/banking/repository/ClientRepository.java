package fr.banking.repository;

import fr.banking.entities.CarteEntity;
import fr.banking.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Long> {

}
