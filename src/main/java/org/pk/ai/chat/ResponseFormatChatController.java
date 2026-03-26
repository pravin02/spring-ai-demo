package org.pk.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/chat/format")
public class ResponseFormatChatController {

    private ChatClient chatClient;

    public ResponseFormatChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/unstructured")
    public Flux<?> unstructuredResponse() {
        return this.chatClient.prompt()
                .user("List down the states of India")
                .stream()
                .content();
    }

    @GetMapping("/structured")
    public IndiaStates structuredResponse() {
        return this.chatClient.prompt()
                .user("List down the states of India")
                .call()
                .entity(IndiaStates.class);
    }

}

record IndiaStates(List<String> states) {
}
