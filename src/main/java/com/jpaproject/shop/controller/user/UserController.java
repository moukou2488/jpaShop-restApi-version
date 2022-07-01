package com.jpaproject.shop.controller.user;

import com.jpaproject.shop.controller.ResultList;
import com.jpaproject.shop.domain.User;
import com.jpaproject.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public UserResponse.CreateUserResponse saveUser(@RequestBody @Valid UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .address(userRequest.getAddress())
                .build();

        Long id = userService.join(user);
        return new UserResponse.CreateUserResponse(id);
    }

    @PatchMapping("/api/users/{id}")
    public UserResponse.UpdateUserResponse editUser(@PathVariable("id") Long id,
                                 @RequestBody @Valid UserRequest userRequest) {
        userService.update(id, userRequest);
        User user = userService.findOne(id);
        return new UserResponse.UpdateUserResponse(id,user.getName());
    }

    @GetMapping("/api/users")
    public ResultList showUsers(@RequestParam(value = "offset", defaultValue = "0") int offset) {
        List<User> findUsers = userService.findUsers(offset);
        List<UserResponse> collect = findUsers.stream().map(m -> new UserResponse(m.getId(), m.getName(), m.getAddress()))
                .collect(Collectors.toList());
        return new ResultList(collect);
    }
}
