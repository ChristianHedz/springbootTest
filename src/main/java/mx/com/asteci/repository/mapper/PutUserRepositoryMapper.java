package mx.com.asteci.repository.mapper;

import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.repository.entity.User;
import org.springframework.stereotype.Component;


@Component
public class PutUserRepositoryMapper {

    public User user_put_request_to_entity(UserPutRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}

