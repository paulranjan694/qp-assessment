package com.qpAssessment.qp_assessment.exceptions;

public class UnAuthorizedAccessException extends RuntimeException{
    public UnAuthorizedAccessException(String message){
        super(message);
    }
}
