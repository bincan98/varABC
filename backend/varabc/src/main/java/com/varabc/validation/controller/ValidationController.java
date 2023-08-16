package com.varabc.validation.controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.varabc.validation.Service.DockerService;
import com.varabc.validation.Service.ValidationService;
import com.varabc.validation.domain.dto.*;
import com.varabc.validation.domain.util.FileData;
import com.varabc.validation.mapper.ValidationMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
//@Component // Add this annotation to register the class as a Spring bean
public class ValidationController {
    private final ValidationService validationService;
    private final ValidationMapper validationMapper;
    private final DockerService dockerService;

    @GetMapping("/ping")
    public void pingDocker() {
        System.out.println(dockerService.pingDocker());
        return;
    }
    @PostMapping("compilePython")
    public ResponseEntity<CompileResultDto> compilePy(@RequestBody ValidateDataDto validateDataDto) throws Exception{
        TestCaseDto testCaseDto= validationService.getPublicTestCaseDtoByProblemNo(validateDataDto.getProblemNo());

        List<FileData> inputFiles= validationService.getUrlIntoText(testCaseDto.getInputFiles());
        List<FileData> outputFiles= validationService.getUrlIntoText(testCaseDto.getOutputFiles());


        ProblemRestrictionDto problemRestrictionDto =validationService.getProblemRestriction(validateDataDto.getProblemNo());
        ValidateDto validateDto= validationMapper.mapToValidateDto(validateDataDto,problemRestrictionDto,inputFiles,outputFiles,1);
        //파이썬 서버로 요청 보내기
        System.out.println(problemRestrictionDto);
        HttpStatus status=HttpStatus.OK;

        //service단에서 파이썬 서버로 요청을 보내고 그에 대한 응답을 받게끔 처리
        String pythonServerUrl = "http://varabc.com:5000";
        CompileResultDto compileResultDto = validationService.sendRequestCompile(
                pythonServerUrl, validateDto);
        System.out.println(compileResultDto);

        return new ResponseEntity<CompileResultDto>(compileResultDto, status);
    }
    //파이썬 서버로 요청 보내기
    @PostMapping("sendvalidatePython")
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
        String pythonServerUrl = "http://varabc.com:5000/";
        ValidationResultDto validationResultDto = validationService.sendRequestValidation(
                pythonServerUrl, validateDto);
        System.out.println(validationResultDto);
        validationService.saveValidationResult(validationResultDto, validateDto,1,
                1L,0);

        return new ResponseEntity<ValidationResultDto>(validationResultDto, status);
    }
    //파이썬 서버로 도커컨테이너로 요청 격리해 보내기
    @PostMapping("sendValidatePyDocker")
    public ResponseEntity<ValidationResultDto> validatePyDocker(@RequestBody ValidateDataDto validateDataDto) throws Exception{
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
        // 도커 컨테이너 생성 및 실행
        String containerId = dockerService.startPythonEvaluationContainer();


        String pythonServerUrl = "http://" + dockerService.containerIpAddress(containerId) + ":5005/";
        System.out.println(pythonServerUrl);
        ValidationResultDto validationResultDto = validationService.sendRequestValidation(
                pythonServerUrl, validateDto);
        // 도커 컨테이너 종료
        dockerService.stopPythonEvaluationContainer(containerId);


        System.out.println(validationResultDto);
        validationService.saveValidationResult(validationResultDto, validateDto,1,
                1L,0);

        return new ResponseEntity<ValidationResultDto>(validationResultDto, status);
    }

    // 자바 실행하기
    @PostMapping("compileJava")
    public ResponseEntity<CompileResultDto> compileJava(@RequestBody ValidateDataDto validateDataDto) throws Exception{
        TestCaseDto testCaseDto= validationService.getPublicTestCaseDtoByProblemNo(validateDataDto.getProblemNo());
        List<FileData> inputFiles= validationService.getUrlIntoText(testCaseDto.getInputFiles());
        List<FileData> outputFiles= validationService.getUrlIntoText(testCaseDto.getOutputFiles());

        ProblemRestrictionDto problemRestrictionDto =validationService.getProblemRestriction(validateDataDto.getProblemNo());
        ValidateDto validateDto= validationMapper.mapToValidateDto(validateDataDto,problemRestrictionDto,inputFiles,outputFiles,2);
        //파이썬 서버로 요청 보내기
        System.out.println(problemRestrictionDto);
        HttpStatus status=HttpStatus.OK;

        //service단에서 파이썬 서버로 요청을 보내고 그에 대한 응답을 받게끔 처리
        String javaServerUrl = "http://varabc.com:8081";
        CompileResultDto compileResultDto = validationService.sendRequestCompile(
                javaServerUrl, validateDto);
        System.out.println(compileResultDto);

        return new ResponseEntity<CompileResultDto>(compileResultDto, status);
    }

    //자바 서버로 요청 보내기
    @PostMapping("sendvalidateJava")
    public ResponseEntity<ValidationResultDto> validateJava(@RequestBody ValidateDataDto validateDataDto) throws Exception{

        // 자바 채점 서버로 요청 보내기
        TestCaseDto testCaseDto= validationService.getTestCaseDtoByProblemNo(validateDataDto.getProblemNo());
        List<FileData> inputFiles= validationService.getUrlIntoText(testCaseDto.getInputFiles());
        List<FileData> outputFiles= validationService.getUrlIntoText(testCaseDto.getOutputFiles());

        ProblemRestrictionDto problemRestrictionDto =validationService.getProblemRestriction(validateDataDto.getProblemNo());
        ValidateDto validateDto= validationMapper.mapToValidateDto(validateDataDto,problemRestrictionDto,inputFiles,outputFiles,2);
        //파이썬 서버로 요청 보내기
        System.out.println(problemRestrictionDto);

        HttpStatus status=HttpStatus.OK;

        //service단에서 자바 채점 서버로 요청을 보내고 그에 대한 응답을 받게끔 처리
        String javaServerUrl = "http://varabc.com:8081/";
        ValidationResultDto validationResultDto=validationService.sendRequestValidation(javaServerUrl,validateDto);
        System.out.println(validationResultDto);
        validationService.saveValidationResult(validationResultDto, validateDto,1,
                1L,0);

        return new ResponseEntity<ValidationResultDto>(validationResultDto, status);
    }


}
