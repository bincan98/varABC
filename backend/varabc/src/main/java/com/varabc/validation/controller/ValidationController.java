package com.varabc.validation.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.varabc.validation.Service.ValidationService;
import com.varabc.validation.domain.dto.ProblemRestrictionDto;
import com.varabc.validation.domain.dto.TestCaseDto;
import com.varabc.validation.domain.dto.ValidateDataDto;
import com.varabc.validation.domain.dto.ValidateDto;
import com.varabc.validation.domain.dto.ValidationResultDto;
import com.varabc.validation.domain.util.FileData;
import com.varabc.validation.mapper.ValidationMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;


@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
//@Component // Add this annotation to register the class as a Spring bean
public class ValidationController {
    //일단 파이썬 채점에 필요한 여건들부터 차례대로 확인해보자.
    //클라이언트에서 코드와 기타등등을 전달받았다고 가정하고,
    //해당 요청을 현재 로컬서버의 파이썬 채점서버에 전달해서 채점 결과를 받아오는
    //api를 먼저 작성해보자.
    //마무리
    private final ValidationService validationService;
    private final ValidationMapper validationMapper;
    private final AmazonS3 amazonS3;
    private static final Region region = Region.AP_NORTHEAST_2;


    //파이썬 서버로 요청 보내기
    @PostMapping("sendvalidatepy")
    public ResponseEntity<ValidationResultDto> validatePy(@RequestBody ValidateDataDto validateDataDto) throws Exception{
        //DB에서 엔티티를 꺼내와서  ValidationResult ValidateDto의 값을 온전하게 세팅하여 전달함,
        //레포지토리에서 테스트케이스들을 가져오는 로직 수행


        TestCaseDto testCaseDto= validationService.getTestCaseDtoByProblemNo(validateDataDto.getProblemNo());
        //레포지토리에서 문제에 대한 제약사항들을 가져오는 로직 수행
        List<FileData> inputFiles= validationService.getUrlIntoText(testCaseDto.getInputFiles());
        List<FileData> outputFiles= validationService.getUrlIntoText(testCaseDto.getOutputFiles());


        ProblemRestrictionDto problemRestrictionDto =validationService.getProblemRestriction(validateDataDto.getProblemNo());
        ValidateDto validateDto= validationMapper.mapToValidateDto(validateDataDto,problemRestrictionDto,inputFiles,outputFiles,1);
        //파이썬 서버로 요청 보내기
        System.out.println(problemRestrictionDto);
        HttpStatus status=HttpStatus.OK;

        //service단에서 파이썬 서버로 요청을 보내고 그에 대한 응답을 받게끔 처리
        String pythonServerUrl = "http://43.200.245.232:5000/";
        ValidationResultDto validationResultDto = validationService.sendRequestValidation(
                pythonServerUrl, validateDto);
        System.out.println(validationResultDto);
        validationService.saveValidationResult(validationResultDto, validateDto);

        return new ResponseEntity<ValidationResultDto>(validationResultDto, status);
    }

    //자바 서버로 요청 보내기
    @PostMapping("sendvalidatejava")
    public ResponseEntity<ValidationResultDto> validateJava(@RequestBody ValidateDataDto validateDataDto) throws Exception{

        // 자바 채점 서버로 요청 보내기
        TestCaseDto testCaseDto= validationService.getTestCaseDtoByProblemNo(validateDataDto.getProblemNo());
        List<FileData> inputFiles= validationService.getUrlIntoText(testCaseDto.getInputFiles());
        List<FileData> outputFiles= validationService.getUrlIntoText(testCaseDto.getOutputFiles());

        ProblemRestrictionDto problemRestrictionDto =validationService.getProblemRestriction(validateDataDto.getProblemNo());
        ValidateDto validateDto= validationMapper.mapToValidateDto(validateDataDto,problemRestrictionDto,inputFiles,outputFiles,1);
        //파이썬 서버로 요청 보내기
        System.out.println(problemRestrictionDto);

        HttpStatus status=HttpStatus.OK;

        //service단에서 자바 채점 서버로 요청을 보내고 그에 대한 응답을 받게끔 처리
        String javaServerUrl = "http://43.200.245.232:8081/";
        ValidationResultDto validationResultDto=validationService.sendRequestValidation(javaServerUrl,validateDto);
        System.out.println(validationResultDto);
        validationService.saveValidationResult(validationResultDto, validateDto);

        return new ResponseEntity<ValidationResultDto>(validationResultDto, status);
    }

}
