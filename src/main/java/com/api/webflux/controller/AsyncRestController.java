package com.api.webflux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.webflux.model.MessageRequest;
import com.api.webflux.service.AsyncRestCall;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class AsyncRestController {
	Logger logger = LoggerFactory.getLogger(AsyncRestController.class);
	@Autowired
	AsyncRestCall<MessageRequest> asyncRestCall; 
	
	@RequestMapping(path = "/asyncCompletable", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	   public Mono<MessageRequest> getValueAsyncUsingCompletableFuture(@RequestBody MessageRequest request) {	;
	   MDC.put("ID", request.getId().toString());
	   logger.info("Received request...");
        Mono.fromFuture(asyncRestCall.call(request)).subscribe();
        return Mono.just(request);

    }

}
