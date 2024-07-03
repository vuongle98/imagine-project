package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.domain.repositories.FileRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaFileRepository extends JpaRepository<File, UUID>, FileRepository {
}
