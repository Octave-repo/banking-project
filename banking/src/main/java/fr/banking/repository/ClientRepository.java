package fr.banking.repository;

import fr.banking.entities.CarteEntity;
import fr.banking.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
    List<ClientEntity> findClientEntityByNomAndPrenom(String nom, String prenom);
}
