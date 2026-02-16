package mx.com.asteci.repository.mapper;

import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.repository.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostUserRepositoryMapper {

    public User user_post_request_to_entity(UserPostRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}

