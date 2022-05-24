package com.appsbykeegan.watcher_storageapi.repository;

import com.appsbykeegan.watcher_storageapi.entity.table.FileAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Kelvin Keegan on 5/24/2022
 */

@Repository
public interface AppRepository extends JpaRepository<FileAttributes,Long> {

    Optional<FileAttributes> findByFileName(String fileName);

}
