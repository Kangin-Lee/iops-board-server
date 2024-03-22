package org.example.boardserver.service;

import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.ComentDTO;
import org.example.boardserver.repository.ComentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentService {
    private final ComentRepository comentRepository;


    public void saveComent(ComentDTO comentDTO) {


    }
}
