package mx.com.asteci.service.mapper;

import mx.com.asteci.model.response.Status;
import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.repository.entity.User;
import org.springframework.stereotype.Component;


@Component
public class PostUserServiceMapper {


    public UserPostResponse user_entity_to_post_response(User user) {
        UserResponse data = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        Status status = Status.builder()
                .statusCode("201")
                .description("Usuario creado exitosamente")
                .build();
        return UserPostResponse.builder()
                .status(status)
                .data(data)
                .build();
    }
}

