package com.api.webflux.service;

import java.util.concurrent.CompletableFuture;

import com.api.webflux.model.MessageRequest;

public interface AsyncRestCall<T>  {
	
	CompletableFuture<String> call(MessageRequest message); 
	 

}
