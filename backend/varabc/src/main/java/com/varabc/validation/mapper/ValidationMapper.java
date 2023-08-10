package com.varabc.validation.mapper;


import com.varabc.battle.domain.dto.ResultDto;
import com.varabc.battle.domain.dto.SubmitBattleDto;
import com.varabc.problem.domain.entity.ProblemRestriction;
import com.varabc.validation.domain.dto.ProblemRestrictionDto;
import com.varabc.validation.domain.dto.SubmitDto;
import com.varabc.validation.domain.dto.TestCaseDto;
import com.varabc.validation.domain.dto.ValidateDataDto;
import com.varabc.validation.domain.dto.ValidateDto;
import com.varabc.validation.domain.dto.ValidationResultDto;
import com.varabc.validation.domain.entity.Submit;
import com.varabc.validation.domain.util.FileData;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class ValidationMapper {
        //testCaseEntity를 testCaseDto로 변환해기

        public  TestCaseDto testCaseListToDto(List<String> inputFiles, List<String> outputFiles) {
            return TestCaseDto.builder()
                    .inputFiles(inputFiles)
                    .outputFiles(outputFiles)
                    .build();
        }
        //validateDto를  validateDataDto+testCaseDto와 함께 빌드
        public  ValidateDto mapToValidateDto(ValidateDataDto validateDataDto,
                ProblemRestrictionDto problemRestrictionDto, List<FileData> inputFiles,
                List<FileData> outputFiles, int language){
            double timelimit=0.0;
            String lang = "";
            if (language==1){
                timelimit=problemRestrictionDto.getProblemRestrictionPython();
                lang = "python";
            } else if (language==2) {
                timelimit=problemRestrictionDto.getProblemRestrictionJava();
                lang = "java";
            }

            return ValidateDto.builder()
                    .memberNo(validateDataDto.getMemberNo())
                    .problemNo(validateDataDto.getProblemNo())
                    .code(validateDataDto.getCode())
                    .timeLimit(timelimit)
                    .memoryLimit(problemRestrictionDto.getProblemRestrictionMemory())
                    .inputFiles(inputFiles)
                    .outputFiles(outputFiles)
                    .language(lang)
                    .build();
        }

    public  ValidateDto mapToValidateDto(SubmitBattleDto submitBattleDto,
            ProblemRestrictionDto problemRestrictionDto, List<FileData> inputFiles,
            List<FileData> outputFiles, int member){
        double timelimit=0.0;
        if (submitBattleDto.getLanguage().equals("python")){
            timelimit=problemRestrictionDto.getProblemRestrictionPython();
        } else  {
            timelimit=problemRestrictionDto.getProblemRestrictionJava();
        }
        Long memberNo = null;
        if(member==1) memberNo = submitBattleDto.getMember1();
        else memberNo = submitBattleDto.getMember2();
        return ValidateDto.builder()
                .memberNo(memberNo)
                .problemNo(submitBattleDto.getProblemNo())
                .code(submitBattleDto.getCode())
                .timeLimit(timelimit)
                .memoryLimit(problemRestrictionDto.getProblemRestrictionMemory())
                .inputFiles(inputFiles)
                .outputFiles(outputFiles)
                .language(submitBattleDto.getLanguage())
                .build();
    }

        //ProblemRestriction을 Dto로 변환
        public  ProblemRestrictionDto problemRestrictionToDto(ProblemRestriction problemRestriction){
           return ProblemRestrictionDto.builder()
                   .problemNo(problemRestriction.getProblemNo())
                   .problemRestrictionJava(problemRestriction.getProblemRestrictionTimeJava())
                   .problemRestrictionPython(problemRestriction.getProblemRestrictionTimePython())
                   .problemRestrictionMemory(problemRestriction.getProblemRestrictionMemory())
                   .problemRestrictionResign(problemRestriction.isProblemRestrictionResign())
                   .build();
        }

        //validationResultDto->submitEntity로 변환
        public  Submit mapDtoToSubmitEntity(ValidationResultDto validationResultDto, ValidateDto validateDto, int mode){
            return Submit.builder()
                    .memberNo(validateDto.getMemberNo())
                    .problemNo(validateDto.getProblemNo())
                    .submitStatus(validationResultDto.getResult())
                    .submitMode(mode)
                    .submitUsedMemory(validationResultDto.getMemoryUsage())
                    .submitUsedTime(validationResultDto.getExecutionTime())
                    .submitCode(validateDto.getCode())
                    .submitLanguage(validateDto.getLanguage())
                    .competitionResultNo(1)
                    .build();
        }
        //SubmitEntity->SubmitDto로 변환
        public  SubmitDto submitToDto(Submit submit){
            return SubmitDto.builder()
                    .submitNo(submit.getSubmitNo())
                    .submitTime(submit.getSubmitTime())
                    .submitMode(submit.getSubmitMode())
                    .submitCode(submit.getSubmitCode())
                    .submitStatus(submit.getSubmitStatus())
                    .submitUsedMemory(submit.getSubmitUsedMemory())
                    .submitUsedTime(submit.getSubmitUsedTime())
                    .submitLanguage(submit.getSubmitLanguage())
                    .build();
        }
        public  ResultDto dtoToDto(ValidationResultDto validationResultDto, String resultMessage,
                String redirectUrl){
            return ResultDto.builder()
                    .problemNo(validationResultDto.getProblemNo())
                    .result(validationResultDto.getResult())
                    .executionTime(validationResultDto.getExecutionTime())
                    .memoryUsage(validationResultDto.getMemoryUsage())
                    .exceptionMessage(validationResultDto.getExceptionMessage())
                    .resultMessage(resultMessage)
                    .redirectUrl(redirectUrl)
                    .build();
        }
}
