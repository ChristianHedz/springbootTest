package mx.com.asteci.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.com.asteci.model.request.UserPostRequest;
import mx.com.asteci.model.request.UserPutRequest;
import mx.com.asteci.model.response.UserPostResponse;
import mx.com.asteci.model.response.UserPutResponse;
import mx.com.asteci.model.response.UserResponseData;
import mx.com.asteci.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final PostUserService postUserService;
    private final GetAllUserService getAllUserService;
    private final PutUserService putUserService;

    @Override
    public ResponseEntity<UserPostResponse> create(@Valid UserPostRequest request) {
        return postUserService.post_user_service_handler(request);
    }

    @Override
    public ResponseEntity<UserResponseData> getAll() {
        return getAllUserService.get_all_user_service_handler();
    }

    @Override
    public ResponseEntity<UserPutResponse> update(Long id, @Valid UserPutRequest request) {
        return putUserService.put_user_service_handler(id, request);
    }

}
