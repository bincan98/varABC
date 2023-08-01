package com.problemsolvingjava.validate.domain.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ValidationResultDto {
    //채점 결과로 무엇을 받아와야 하는가
    private String result;
    private double executionTime;
    private String memoryUsage;
    private String exceptionMessage;
}

