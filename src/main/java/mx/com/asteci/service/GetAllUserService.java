package mx.com.asteci.service;

import lombok.RequiredArgsConstructor;
import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import mx.com.asteci.service.mapper.GetAllUserServiceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class GetAllUserService {

    private final UserRepository userRepository;
    private final GetAllUserServiceMapper getAllUserServiceMapper;

    public ResponseEntity<UserResponseData> get_all_user_service_handler() {
        List<User> users = userRepository.findAll();
        UserResponseData response = getAllUserServiceMapper.user_entity_list_to_list_response(users);
        return ResponseEntity.ok(response);
    }
}
