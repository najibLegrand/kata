package com.carrefour.kata.ai;

import com.carrefour.kata.domain.DeliveryMethod;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AiAdviceService {

    private static final String SYS_PROMPT = """
        Tu es un assistant Carrefour. Réponds en français, une seule phrase, claire et utile pour un client.
        Ne promets pas de créneau si le planning est inconnu. Pas d’emoji.
        """;

    private final ChatClient chat;

    public AiAdviceService(ChatClient chat) {
        this.chat = chat;
    }

    public String advice(DeliveryMethod method, LocalDate day) {
        return chat.prompt()
                .system(s -> s.text(SYS_PROMPT))
                .user(u -> u.text("Méthode: {method}. Jour: {day}. Donne un conseil court et pratique.")
                        .param("method", method.name())
                        .param("day", day.toString()))
                .call()
                .content();

    }
}
