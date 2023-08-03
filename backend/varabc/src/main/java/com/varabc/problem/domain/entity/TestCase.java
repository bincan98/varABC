package com.varabc.problem.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Table(name = "testcase")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@ToString
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testcaseNo;

    @Column(name = "problem_no", nullable = false)
    private Long problemNo;

    @Column(name = "testcase_input", columnDefinition = "TEXT", nullable = false)
    private String testcaseInput;
    @Column(name = "testcase_output", columnDefinition = "TEXT", nullable = false)
    private String testcaseOutput;

    @Column(name = "testcase_public", nullable = false,columnDefinition = "TINYINT(1) default 0")
    private Boolean testcasePublic;

    @Column(name = "testcase_resign", nullable = false,columnDefinition = "TINYINT(1) default 0")
    private Boolean testcaseResign;

    @Builder
    public TestCase(Long testcaseNo, Long problemNo, String testcaseInput, String testcaseOutput,
            Boolean testcasePublic, Boolean testcaseResign) {
        this.testcaseNo = testcaseNo;
        this.problemNo = problemNo;
        this.testcaseInput = testcaseInput;
        this.testcaseOutput = testcaseOutput;
        this.testcasePublic = testcasePublic;
        this.testcaseResign = testcaseResign;
    }
}