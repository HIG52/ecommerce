package kr.hhplus.be.server.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/publish")
    public String publishMessage(@RequestBody String message) {
        kafkaProducer.sendMessage("test-topic", message);
        return "Message published: " + message;
    }

}
