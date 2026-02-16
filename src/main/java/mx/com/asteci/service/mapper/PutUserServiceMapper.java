package mx.com.asteci.service.mapper;

import mx.com.asteci.model.response.Status;
import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.repository.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PutUserServiceMapper {

    public UserPutResponse user_entity_to_put_response(User user) {
        UserResponse data = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        Status status = Status.builder()
                .statusCode("200")
                .description("Usuario actualizado exitosamente")
                .build();
        return UserPutResponse.builder()
                .status(status)
                .data(data)
                .build();
    }
}

