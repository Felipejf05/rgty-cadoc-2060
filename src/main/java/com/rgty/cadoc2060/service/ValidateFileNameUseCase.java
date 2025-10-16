package com.rgty.cadoc2060.service;

import com.rgty.cadoc2060.repository.CadocCompanyRepository;
import com.rgty.cadoc2060.validator.PatternValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateFileNameUseCase {

    private final CadocCompanyRepository cadocCompanyRepository;
    private final PatternValidator patternValidator;

    public boolean validateFileName(final String fileName){
        var cadocCompanies = cadocCompanyRepository.findAll();
        return patternValidator.validateFilePattern(fileName, cadocCompanies);
    }
}
