package com.api.webflux.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

 
import com.api.webflux.model.MessageRequest;
import com.api.webflux.repository.MessageRepository;

 

@Service
public class MessageServiceImpl implements AsyncRestCall<MessageRequest>{
	Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public CompletableFuture<String> call(MessageRequest message) {
		// TODO Auto-generated method stub
		logger.info("Async operation started....");
		 return CompletableFuture.supplyAsync(() -> processRequest(message));
	}
	
	private String processRequest(MessageRequest request) { 
		  MDC.put("ID", request.getId().toString());
		boolean  res = callSoap(gePayload(request.getMessage()));
	    if(res)
	    	persistData(request);
		return "SUCCESS";
	}

	
	private void persistData(MessageRequest message) {
		logger.info("Saving data to H2 database....");
		MessageRequest save = messageRepository.save(message);
		System.out.println(save);
	}
	
	private String gePayload(String message) { 
		   StringBuilder payload = new StringBuilder(); 
		   payload.append("<s11:Envelope><soapenv:Header><soapenv:Body>");
		   payload.append("<urn:post_message>");
		   payload.append(message); 
		   payload.append("</urn:post_message>"); 
		   payload.append("</soapenv:Body>"); 
		   payload.append("</soapenv:Header></s11:Envelope>");    
		   return payload.toString() ;
		}

	private boolean callSoap(String payload) {
		logger.info("Invoking soap service..");
		try {
		       WebClient webclient = WebClient.builder()
		                             // make sure the path is absolute 
		                             .baseUrl("http://localhost:8088/mockmy SOAP forum_webservices") 
		                             .build() ;

		         webclient.post()		       
		                .contentType(MediaType.TEXT_XML)
		                .bodyValue(payload)		      
		                .retrieve()
		                .bodyToMono(String.class)
		                .block();

		    
	}catch(Exception e) {
		
	}
		return true;
	}
 

}
