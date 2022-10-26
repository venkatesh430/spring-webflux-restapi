package com.api.webflux.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.webflux.model.MessageRequest;

@Repository
public interface MessageRepository extends CrudRepository<MessageRequest, Integer>  {

}
