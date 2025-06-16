package com.kakan.user_service.service.impl;

import com.fasterxml.jackson.databind.jsontype.impl.SubTypeValidator;
import com.kakan.user_service.dto.SubjectScoreDto;
import com.kakan.user_service.dto.request.ScoreRequest;
import com.kakan.user_service.dto.response.GpaResponseDto;
import com.kakan.user_service.dto.response.ViewScoreDetail;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.pojo.Score;
import com.kakan.user_service.pojo.Subject;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.repository.ScoreRepository;
import com.kakan.user_service.repository.SubjectRepository;
import com.kakan.user_service.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SubjectRepository subjectRepository;


    public double calculateGpa(int accountId, List<SubjectScoreDto> dtos) {


        try{
            Account account = accountRepository.findAccountById(accountId);

            if (account == null) {
                throw new RuntimeException("Account not found");
            }
            List<Score> entities = new ArrayList<>();
            for (SubjectScoreDto dto : dtos) {
                Subject subject = subjectRepository.findSubjectBySubjectId(dto.getSubjectId());

                Score score = new Score();
                score.setAccount(account);
                score.setSubject(subject);
                score.setScoreYear10(dto.getScoreYear10());
                score.setScoreYear11(dto.getScoreYear11());
                score.setScoreYear12(dto.getScoreYear12());
                entities.add(score);
            }
            List<Score> saved = scoreRepository.saveAll(entities);
            double year10Total = 0.0;
            double year11Total = 0.0;
            double year12Total = 0.0;

            int n = saved.size();
            for(Score s : saved){
                year10Total += s.getScoreYear10();
                year11Total += s.getScoreYear11();
                year12Total += s.getScoreYear12();
            }
            double avg10 = n>0 ? year10Total / n : 0;
            double avg11 = n>0 ? year11Total / n : 0;
            double avg12 = n>0 ? year12Total / n : 0;

            double gpa = (avg10 + avg11 + avg12) / 3.0;
            double roundedGpa = Math.round(gpa * 100.0) / 100.0;
            return roundedGpa;
        }catch (Exception e) {
            throw new RuntimeException("Error calculating GPA: " + e.getMessage());
        }
    }
    public List<ViewScoreDetail> getScoreDetails(int accountId) {

        List<Score> scores = scoreRepository.findScoreByAccount_Id(accountId);
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
}
