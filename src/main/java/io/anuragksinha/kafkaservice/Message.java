package io.anuragksinha.kafkaservice;

import java.time.LocalDateTime;

public record Message(String message, LocalDateTime created) {
}
