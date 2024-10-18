package com.backend.projetointegrador.resources;

import com.backend.projetointegrador.domain.dtos.UserRequestDTO;
import com.backend.projetointegrador.domain.dtos.UserResponseDTO;
import com.backend.projetointegrador.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(Authentication authentication) {
        UserResponseDTO responseDTO = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.create(userRequestDTO, "CLIENT");
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/probe")
    public ResponseEntity<String> probe() {
        return ResponseEntity.ok().body("OK");
    }
}
