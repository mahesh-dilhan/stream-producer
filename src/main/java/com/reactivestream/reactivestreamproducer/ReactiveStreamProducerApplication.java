package com.reactivestream.reactivestreamproducer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Log4j2
@EnableBinding({Source.class})
@SpringBootApplication
public class ReactiveStreamProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveStreamProducerApplication.class, args);
	}
}



@RestController
class StreamProducerHandler {
	private final MessageChannel out;

	StreamProducerHandler(Source source) {
		this.out = source.output();
	}

	@PostMapping("/person")
	public void write(@RequestBody Person  person) {
		Message<Person> msg = MessageBuilder.withPayload(person)
				.build();
		this.out.send(msg);
	}
}




@Data
@NoArgsConstructor
@AllArgsConstructor
class Person{
 private String name;
}