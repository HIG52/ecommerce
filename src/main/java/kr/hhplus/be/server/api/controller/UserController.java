package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.dto.UserRequestDTO;
import kr.hhplus.be.server.api.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/api/users/{userId}/point")
    public ResponseEntity<UserResponseDTO> getUserPoint(
            @PathVariable(name = "userId") int userId) {

        UserResponseDTO response = new UserResponseDTO();

        response.setUserId(userId);
        response.setBalance(20000);

        if(userId <= 0) {
            response.setMessage("사용자가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/api/users/{userId}/charge")
    public ResponseEntity<UserResponseDTO> userPointCharge(
            @PathVariable(name = "userId") int userId,
            @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO response = new UserResponseDTO();

        response.setUserId(userId);
        response.setBalance(20000);

        if(userId <= 0) {
            response.setMessage("사용자가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}