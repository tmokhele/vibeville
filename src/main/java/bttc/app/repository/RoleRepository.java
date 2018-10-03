package bttc.app.repository;

import bttc.app.model.RoleName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepository {
    public <Role> Optional findByName(RoleName roleUser) {
        return null;
    }
}
