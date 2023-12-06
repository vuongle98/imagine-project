package com.vuongle.imaginepg.infrastructure.persistance;

import com.vuongle.imaginepg.domain.entities.File;
import com.vuongle.imaginepg.domain.repositories.FileRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaFileRepository extends JpaRepository<File, UUID>, FileRepository {
}
