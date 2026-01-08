package com.shoppingcart.repository;

import com.shoppingcart.entity.Role;
import com.shoppingcart.entity.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRoles roleName);
    Boolean existsByRoleName(UserRoles roleName);
}
