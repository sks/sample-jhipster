package io.github.sks.samplejhipster.repository;

import io.github.sks.samplejhipster.domain.MyTestEntity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyTestEntity entity.
 */
@SuppressWarnings("unused")
public interface MyTestEntityRepository extends JpaRepository<MyTestEntity,Long> {

}
