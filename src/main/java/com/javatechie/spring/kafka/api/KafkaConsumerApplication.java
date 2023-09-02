package com.javatechie.spring.kafka.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class KafkaConsumerApplication {

	List<String> messages = new ArrayList<>();

	User userFromTopic = null;

	@GetMapping("/consumeStringMessage")
	public List<String> consumeMsg() {
		return messages;
	}

	@GetMapping("/consumeJsonMessage")
	public User consumeJsonMessage() {
		return userFromTopic;
	}

	@KafkaListener(groupId = "group100", topics = "quickstart001", containerFactory = "kafkaListenerContainerFactory")
	public List<String> getMsgFromTopic(String data) {
		messages.add(data);
		System.out.println("data: "+data);
		return messages;
	}



	@KafkaListener(groupId = "grp1", topics = "Jsonconsumer", containerFactory = "userKafkaListenerContainerFactory")
	public User getJsonMsgFromTopic(User user) {
		if(user.getId() !=null)
		{
			return user;
		}
		return new User();
	}


	@KafkaListener(groupId = "001grp", topics = "testtingoffset03", containerFactory = "kafkaListenerContainerFactoryOwnImp")
	public List<String> offsettest001(String data) {
		messages.add(data);
		System.out.println("data: "+data);
		return messages;
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable Long id) {
		// Update the user with the given ID and return status code 204 No Content


				//return ResponseEntity.noContent().build();

		return ResponseEntity.notFound().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}
}
