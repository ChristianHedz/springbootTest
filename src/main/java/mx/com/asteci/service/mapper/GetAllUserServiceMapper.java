package mx.com.asteci.service.mapper;

import mx.com.asteci.model.response.Status;
import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.model.response.UserResponse;
import mx.com.asteci.repository.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GetAllUserServiceMapper {

    public UserResponseData user_entity_list_to_list_response(List<User> users) {
        List<UserResponse> dataList = users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .toList();
        
        Status status = Status.builder()
                .statusCode("200")
                .description("Usuarios obtenidos exitosamente")
                .build();
        
        return UserResponseData.builder()
                .status(status)
                .data(dataList)
                .build();
    }
}

