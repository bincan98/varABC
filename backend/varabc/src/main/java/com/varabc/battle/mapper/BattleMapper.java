package com.varabc.battle.mapper;

import com.varabc.battle.domain.dto.BattleInfoDto;
import com.varabc.battle.domain.dto.BattleUrlDto;
import com.varabc.battle.domain.dto.StartBattleDto;
import com.varabc.battle.domain.entity.CompetitionResult;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class BattleMapper {

    public CompetitionResult createEntity(Long memberNo) {
        return CompetitionResult.builder()
                .competitionResultT1M1No(memberNo)
                .competitionResultMemberCount(1)
                .competitionResultResign(false)
                .build();
    }


    public BattleUrlDto urlToDto(String url1, String url2) {
        return  BattleUrlDto.builder()
                .url1(url1)
                .url2(url2)
                .build();
    }

    public BattleInfoDto getBattleInfo(String roomCode, StartBattleDto startBattleDto,
            String email1, String email2) {
        if(startBattleDto.getCompetitionTeam()==1){
            return BattleInfoDto.builder()
                    .team(startBattleDto.getCompetitionTeam())
                    .problemNo(startBattleDto.getProblemNo())
                    .m1(startBattleDto.getCompetitionResultT1M1No())
                    .m2(startBattleDto.getCompetitionResultT1M2No())
                    .member1Email(email1)
                    .member2Email(email2)
                    .build();
        }else{
            return BattleInfoDto.builder()
                    .team(startBattleDto.getCompetitionTeam())
                    .problemNo(startBattleDto.getProblemNo())
                    .m1(startBattleDto.getCompetitionResultT2M1No())
                    .m2(startBattleDto.getCompetitionResultT2M2No())
                    .member1Email(email1)
                    .member2Email(email2)
                    .build();
        }

    }
}
