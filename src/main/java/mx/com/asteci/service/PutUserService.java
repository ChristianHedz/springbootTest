package mx.com.asteci.service;

import lombok.RequiredArgsConstructor;
import mx.com.asteci.exception.ResourceNotFoundException;
import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.repository.UserRepository;
import mx.com.asteci.repository.entity.User;
import mx.com.asteci.repository.mapper.PutUserRepositoryMapper;
import mx.com.asteci.service.mapper.PutUserServiceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PutUserService {

    private final UserRepository userRepository;
    private final PutUserRepositoryMapper putUserRepositoryMapper;
    private final PutUserServiceMapper putUserServiceMapper;

    public ResponseEntity<UserPutResponse> put_user_service_handler(Long id, UserPutRequest request) {
        User userDetails = putUserRepositoryMapper.user_put_request_to_entity(request);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        
        User updatedUser = userRepository.save(user);
        UserPutResponse response = putUserServiceMapper.user_entity_to_put_response(updatedUser);
        return ResponseEntity.ok(response);
    }
}
