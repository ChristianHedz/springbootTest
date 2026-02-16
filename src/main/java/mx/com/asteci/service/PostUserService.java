package mx.com.asteci.service;

import lombok.RequiredArgsConstructor;
import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import mx.com.asteci.repository.mapper.PostUserRepositoryMapper;
import mx.com.asteci.service.mapper.PostUserServiceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PostUserService {

    private final UserRepository userRepository;
    private final PostUserRepositoryMapper postUserRepositoryMapper;
    private final PostUserServiceMapper postUserServiceMapper;

    public ResponseEntity<UserPostResponse> post_user_service_handler(UserPostRequest request) {
        User user = postUserRepositoryMapper.user_post_request_to_entity(request);
        User savedUser = userRepository.save(user);
        UserPostResponse response = postUserServiceMapper.user_entity_to_post_response(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
