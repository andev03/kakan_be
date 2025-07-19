package com.kakan.user_service.service.impl;

import com.fasterxml.jackson.databind.jsontype.impl.SubTypeValidator;
import com.kakan.user_service.dto.SubjectScoreDto;
import com.kakan.user_service.dto.request.ScoreRequest;
import com.kakan.user_service.dto.response.BlockScoreResponeDto;
import com.kakan.user_service.dto.response.CalculateScoreDto;
import com.kakan.user_service.dto.response.GpaResponseDto;
import com.kakan.user_service.dto.response.ViewScoreDetail;
import com.kakan.user_service.mapper.SubjectMapper;
import com.kakan.user_service.pojo.*;
import com.kakan.user_service.repository.*;
import com.kakan.user_service.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kakan.user_service.mapper.SubjectMapper.SUBJECT_NAME_MAP;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    UserInformationRepository userInformationRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    BlockRepository blockRepository;

    public double calculateGpa(List<SubjectScoreDto> dtos) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            boolean isCalculate = scoreRepository.existsByAccount_Id(account.getId());
            List<Score> entities = new ArrayList<>();
            for (SubjectScoreDto dto : dtos) {

                String subjectKey = dto.getSubject(); // e.g., "toan"
                String subjectName = SUBJECT_NAME_MAP.get(subjectKey);

                if (subjectName == null) {
                    throw new RuntimeException("Invalid subject key: " + subjectKey);
                }

                Subject subject = subjectRepository.findBySubjectName(subjectName);
                if (subject == null) {
                    throw new RuntimeException("Subject not found for name: " + subjectName);
                }
                List<Score> scoreExist = scoreRepository.findScoreByAccount_Id(account.getId());
                for (Score existingScore : scoreExist) {
                    if (existingScore.getSubject().getSubjectName().equals(subjectName)) {
                        existingScore.setScoreYear10(dto.getScoreYear10());
                        existingScore.setScoreYear11(dto.getScoreYear11());
                        existingScore.setScoreYear12(dto.getScoreYear12());
                        entities.add(existingScore);
                    }
                }
                if (!isCalculate) {// If no existing score found, create a new one
                    Score score = new Score();
                    score.setAccount(account);
                    score.setSubject(subject);
                    score.setScoreYear10(dto.getScoreYear10());
                    score.setScoreYear11(dto.getScoreYear11());
                    score.setScoreYear12(dto.getScoreYear12());
                    entities.add(score);
                }
            }
            List<Score> saved = scoreRepository.saveAll(entities);
            double year10Total = 0.0;
            double year11Total = 0.0;
            double year12Total = 0.0;

            int n = saved.size();
            for (Score s : saved) {
                year10Total += s.getScoreYear10();
                year11Total += s.getScoreYear11();
                year12Total += s.getScoreYear12();
            }
            double avg10 = n > 0 ? year10Total / n : 0;
            double avg11 = n > 0 ? year11Total / n : 0;
            double avg12 = n > 0 ? year12Total / n : 0;

            double gpa = (avg10 + avg11 + avg12) / 3.0;
            double roundedGpa = Math.round(gpa * 100.0) / 100.0;

            UserInformation userInformation = userInformationRepository.findByAccountId(account.getId());
            userInformation.setGpa(roundedGpa);
            userInformationRepository.save(userInformation);
            return roundedGpa;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating GPA: " + e.getMessage());
        }
    }


    public List<ViewScoreDetail> getScoreDetails() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Score> scores = scoreRepository.findScoreByAccount_Id(account.getId());
        List<ViewScoreDetail> viewScoreDetail = new ArrayList<>();
        for (Score score : scores) {
            Subject subject = subjectRepository.findSubjectBySubjectId(score.getSubject().getSubjectId());
            ViewScoreDetail viewScoreDetails = new ViewScoreDetail();
            viewScoreDetails.setSubjectName(subject.getSubjectName());
            viewScoreDetails.setScoreYear10(score.getScoreYear10());
            viewScoreDetails.setScoreYear11(score.getScoreYear11());
            viewScoreDetails.setScoreYear12(score.getScoreYear12());
            viewScoreDetail.add(viewScoreDetails);
        }
        return viewScoreDetail;
    }

    public List<BlockScoreResponeDto> calculateBlockScore(List<SubjectScoreDto> dtos) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            boolean isCalculate = scoreRepository.existsByAccount_Id(account.getId());
            List<Score> entities = new ArrayList<>();
            for (SubjectScoreDto dto : dtos) {

                String subjectKey = dto.getSubject(); // e.g., "toan"
                String subjectName = SUBJECT_NAME_MAP.get(subjectKey);

                if (subjectName == null) {
                    throw new RuntimeException("Invalid subject key: " + subjectKey);
                }

                Subject subject = subjectRepository.findBySubjectName(subjectName);
                if (subject == null) {
                    throw new RuntimeException("Subject not found for name: " + subjectName);
                }
                for (Score existingScore : scoreRepository.findScoreByAccount_Id(account.getId())) {
                    if (existingScore.getSubject().getSubjectName().equals(subjectName)) {
                        existingScore.setScoreYear10(dto.getScoreYear10());
                        existingScore.setScoreYear11(dto.getScoreYear11());
                        existingScore.setScoreYear12(dto.getScoreYear12());
                        entities.add(existingScore);
                    }
                }
                if (!isCalculate) {// If no existing score found, create a new one
                    Score score = new Score();
                    score.setAccount(account);
                    score.setSubject(subject);
                    score.setScoreYear10(dto.getScoreYear10());
                    score.setScoreYear11(dto.getScoreYear11());
                    score.setScoreYear12(dto.getScoreYear12());
                    entities.add(score);
                }
            }
            scoreRepository.saveAll(entities);
            List<Block> blocks = blockRepository.findAll();
            List<Score> scores = scoreRepository.findScoreByAccount_Id(account.getId());
            List<BlockScoreResponeDto> result = new ArrayList<>();

            for (Block block : blocks) {
                double totalScore = 0.0;

                for (Subject subject : block.getSubjects()) {
                    Score found = null;
                    for (Score score : scores) {
                        if (score.getSubject().getSubjectId().equals(subject.getSubjectId())) {
                            found = score;
                            break;
                        }
                    }
                    if (found != null) {
                        double averageScore = (found.getScoreYear10() + found.getScoreYear11() + found.getScoreYear12()) / 3.0;
                        totalScore = Math.round((totalScore + averageScore)* 100.0) / 100.0; // Round to 2 decimal places
                    }
                }
                result.add(new BlockScoreResponeDto(block.getCode(), totalScore));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CalculateScoreDto calculateScore(List<SubjectScoreDto> dtos)
    {
        double gpa = calculateGpa(dtos);

        List<BlockScoreResponeDto> blockScores = calculateBlockScore(dtos);

        CalculateScoreDto calculateScoreDto = new CalculateScoreDto();
        calculateScoreDto.setGpa(gpa);
        calculateScoreDto.setBlockScores(blockScores);
        return calculateScoreDto;
    }


}




