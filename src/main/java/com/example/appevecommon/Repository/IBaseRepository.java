package com.example.appevecommon.Repository;

import com.example.appevecommon.Models.Base.BaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface IBaseRepository<X extends BaseEntity> extends JpaRepository<X,Long>, JpaSpecificationExecutor<X> {

    //SOFT DELETE, native queries to ignore archived = true from the SQLRestriction in BaseEntity
    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_archived = true WHERE id = :id", nativeQuery = true)
    void archive(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_archived = false WHERE id = :id", nativeQuery = true)
    void unarchive(@Param("id") Long id);

    @Query(value = "SELECT * FROM #{#entityName}", nativeQuery = true)
    List<X> findAllIncludingInactive();

    @Query(value = "SELECT * FROM #{#entityName} WHERE id = :id", nativeQuery = true)
    Optional<X> findByIdIncludingInactive(@Param("id") Long id);


}