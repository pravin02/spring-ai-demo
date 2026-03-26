package org.pk.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat/advisor")
public class AdvisorChatController {

    private ChatClient chatClient;

    public AdvisorChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/stream")
    public Flux<String> advisor(@RequestParam("How Java is secure than other programming languages?") String prompt) {
        String systemInstructions = """
                Your are a senior coach and PHD holder in information technology,
                You know only about java programming language,
                you can mentor on Java
                If asked about anything else, respond: "I am java coach, I can help only java related questions."
                """;
        return this.chatClient
                .prompt()
                .user(prompt)
                .system(systemInstructions)
                .stream()
                .content();
    }

    @GetMapping("/article/stream")
    public Flux<String> advisorArticle(@RequestParam("How Java is secure than other programming languages?") String topic) {
        String systemInstructions = """
                Blog post generation guidelines:
                
                1. Length & purpose: 
                    Generate 500 word blog post that inform and engage general audiences.
                
                2. Structure:
                    - Introduction: Honk readers and establish the topic's relevance
                    - Body: Develop 3 main points with supporting evidence and examples
                    - Conclusion: summarize key takeaways and include a call-to-action.
                
                3. Content Requirements:
                    - Include real world application or case studies
                    - Incorporate relevant statistics or data points when appropriate
                    - Explain benefits/implications clearly for non-experts
                
                4. Tone & style:
                    - Write in an informative yet conversational voice
                    - Use accessible language while maintaining authority
                    - Break up text with subheading and short paragraph
                
                5. Response Format: Deliver complete, ready-to-publish posts with ta suggested title.   
                
                """;
        return this.chatClient
                .prompt()
                .user(u -> u.text("Write me a blog post about {topic}")
                        .param("topic", topic))
                .system(systemInstructions)
                .stream()
                .content();
    }

}
