package mx.com.asteci.repository;

import mx.com.asteci.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Busca un usuario por su email
     * @param email el correo electrónico del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<User> findByEmail(String email);
}