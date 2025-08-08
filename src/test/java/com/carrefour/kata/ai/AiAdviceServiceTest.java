package com.carrefour.kata.ai;

import com.carrefour.kata.domain.DeliveryMethod;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AiAdviceServiceTest {

    /** ChatModel fake qui renvoie toujours la même génération. */
    static class FakeModel implements ChatModel {
        @Override
        public ChatResponse call(Prompt prompt) {
            return new ChatResponse(
                    List.of(new Generation(new AssistantMessage("Conseil_fake")))
            );
        }
    }

    @Test
    void advice_returns_text_from_model() {
        // given
        ChatClient client = ChatClient.builder(new FakeModel()).build();
        AiAdviceService service = new AiAdviceService(client);

        // when
        String out = service.advice(DeliveryMethod.DELIVERY, LocalDate.of(2025, 8, 8));

        // then
        assertThat(out).isEqualTo("Conseil_fake");
    }
}
