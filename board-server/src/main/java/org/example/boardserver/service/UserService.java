package org.example.boardserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.entity.UserEntity;
import org.example.boardserver.repository.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void signUp(UserDTO userDTO) {
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setName(userDTO.getName());
        userEntity.setTel(userDTO.getTel());
        userEntity.setPassword(encryptedPassword);
        userEntity.setConfirmPassword(encryptedPassword);

        userRepository.save(userEntity);
    }

    public boolean login(UserDTO userDTO) {
        //이메일로 사용자 찾기
        UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());

        //사용자가 존재하고 입력한 비밀번호가 일치하는지 확인
        return userEntity != null && passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword());
    }


}
