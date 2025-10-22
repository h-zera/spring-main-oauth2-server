package com.hzera.oauth2.spring.main.oauth2.server.common.interfaces;

import com.hzera.oauth2.spring.main.oauth2.server.common.domain.HZeraPage;

import java.util.Optional;

public interface SNACrudRepository<T, I> {

    Optional<T> findById(I id);

    HZeraPage<T> findAll(Integer pageNumber, Integer pageSize, String sort);

    T save(T entity);

    void deleteById(I id);
}
