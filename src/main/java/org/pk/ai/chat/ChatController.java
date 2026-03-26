package org.pk.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping
    public String chat(@RequestParam("How Java is secure than other programming languages?") String prompt) {
        return this.chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    @GetMapping("/stream")
    public Flux<String> streamChat(@RequestParam("How Java is secure than other programming languages?") String prompt) {
        return this.chatClient
                .prompt()
                .user(prompt)
                .stream()
                .content();
    }

    @GetMapping("/stream/response")
    public ChatResponse streamChatResponse(@RequestParam("How Java is secure than other programming languages?") String prompt) {
        return this.chatClient
                .prompt()
                .user(prompt)
                .call()
                .chatResponse();
    }


}
