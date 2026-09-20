package com.example.appevecommon.Repository;

import com.example.appevecommon.Models.Base.Archivable;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface IArchivableRepository<X extends Archivable> extends IBaseRepository<X> {

    @Query(value = "SELECT * FROM #{#entityName}", nativeQuery = true)
    List<X> findAllIncludingInactive();

    //SOFT DELETE, native queries to ignore archived = true from SQLRestriction in Archivable
    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_archived = true WHERE id = :id", nativeQuery = true)
    void archive(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_archived = false WHERE id = :id", nativeQuery = true)
    void unarchive(@Param("id") Long id);
}