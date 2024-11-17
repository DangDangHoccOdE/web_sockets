package com.example.websockets.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@Configuration
@EnableWebSocketMessageBroker // Kích hoạt hỗ trợ WebSocket với STOMP để xử lý tin nhắn thông qua message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user"); // Kích hoạt một message broker đơn giản để xử lý các tin nhắn gửi đến các đích /user
        registry.setApplicationDestinationPrefixes("/app"); // Các tin nhắn được client gửi lên server phải bắt đầu bằng tiền tố /app. Server sẽ xử lý chúng thông qua các phương thức @MessageMapping.
        registry.setUserDestinationPrefix("/user"); //Cấu hình tiền tố /user cho các tin nhắn gửi đến từng người dùng cụ thể (cá nhân hóa).
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Đây là URL mà client sẽ sử dụng để kết nối WebSocket.
                .withSockJS(); // Kích hoạt hỗ trợ SockJS cho js sd fallback
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
 // Sử dụng Jackson để chuyển đổi các thông điệp STOMP thành JSON.
        return false;
        // Khi trả về false, Spring sẽ không ghi đè danh sách MessageConverter mặc định mà sẽ thêm vào.
    }
}
