package com.pro.tameit.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {

        private String content;
        private String sender;
        private String recipient;
}
