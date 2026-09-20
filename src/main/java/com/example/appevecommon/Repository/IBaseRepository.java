package com.example.appevecommon.Repository;

import com.example.appevecommon.Models.Base.BaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


@NoRepositoryBean
public interface IBaseRepository<X extends BaseEntity> extends JpaRepository<X,Long>, JpaSpecificationExecutor<X> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE id = :id", nativeQuery = true)
    Optional<X> findByIdIncludingInactive(@Param("id") Long id);


}
