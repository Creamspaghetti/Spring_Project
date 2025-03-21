package com.fp.muut.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fp.muut.dto.MusicalAndHallDTO;
import com.fp.muut.repository.MainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final MainRepository mainRepository;

	// 모든 뮤지컬과 연관된 홀 데이터를 조회
    public List<MusicalAndHallDTO> findmusicalDatas() {
        List<MusicalAndHallDTO> musicals = mainRepository.findAllMusicalData();
        return musicals;
    }
}
