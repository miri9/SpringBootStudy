package org.zerock.asso1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.asso1.domain.AttachFile;

public interface AttachFileRepository extends JpaRepository<AttachFile,Long> {
    
}