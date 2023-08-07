package com.varabc.battle.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CompetitionResultDto {
    private Long competitionResultNo;

    private Long competitionResultT1M1No;

    private Long competitionResultT1M2No;

    private Long competitionResultT2M1No;

    private Long competitionResultT2M2No;
    private int competitionResultMemberCount;

    private int competitionResultRecord;

    @Builder
    public CompetitionResultDto(Long competitionResultNo, Long competitionResultT1M1No,
            Long competitionResultT1M2No, Long competitionResultT2M1No,
            Long competitionResultT2M2No,
            int competitionResultMemberCount, int competitionResultRecord) {
        this.competitionResultNo = competitionResultNo;
        this.competitionResultT1M1No = competitionResultT1M1No;
        this.competitionResultT1M2No = competitionResultT1M2No;
        this.competitionResultT2M1No = competitionResultT2M1No;
        this.competitionResultT2M2No = competitionResultT2M2No;
        this.competitionResultMemberCount = competitionResultMemberCount;
        this.competitionResultRecord = competitionResultRecord;
    }
}
