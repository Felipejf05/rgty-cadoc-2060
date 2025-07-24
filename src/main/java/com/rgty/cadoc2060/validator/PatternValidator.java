package com.rgty.cadoc2060.validator;

import com.rgty.cadoc2060.common.helper.LogGenerator;
import com.rgty.cadoc2060.domain.CadocCompany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rgty.cadoc2060.common.CadocConstants.EXPECTED_EXTENSION;
import static com.rgty.cadoc2060.common.CadocConstants.EXPECTED_PREFIX;
import static com.rgty.cadoc2060.common.CadocConstants.GROUPS_LENGTH;
import static com.rgty.cadoc2060.common.CadocConstants.UNDEFINED_FORMAT;

@Component
@Slf4j
@RequiredArgsConstructor
public class PatternValidator {

    private final LogGenerator logGenerator;
    private final DateValidator dateValidator;

    /**
     * Exemplo de nome válido:
     * cadoc-2060_FINTECH001_20250714_120000.xml
     *
     * @param fileName       nome do arquivo enviado
     * @param cadocCompanies lista de empresas válidas
     * @return true se o nome estiver no formato esperado e a empresa for válida
     */

    public boolean validateFilePattern(final String fileName, List<CadocCompany> cadocCompanies) {
        final var groups = fileName.split("_");

        if (groups.length != GROUPS_LENGTH) {
            log.info("O arquivo {}{}", fileName, UNDEFINED_FORMAT);
            return false;
        }

        final var prefix = groups[0];
        final var companyName = groups[1];
        final var date = groups[2];
        final var timestampWithExtension = groups[3];

        if (!EXPECTED_PREFIX.equalsIgnoreCase(prefix)) {
            log.info(logGenerator.logMsg(fileName, "Prefixo inválido"));

            return false;
        }
        if(cadocCompanies.stream().noneMatch(m  -> m.getCompanyName().equalsIgnoreCase(companyName))){
            log.info(logGenerator.logMsg(fileName, "Empresa não reconhecida"));
            return false;
        }
        if(!dateValidator.isYearMonthDay(date)){
            log.info(logGenerator.logMsg(fileName, "Data inválida no nome do arquivo"));
            return false;
        }

        final var timestampParts = timestampWithExtension.split("\\.");

        if(timestampParts.length != 2){
            log.info(logGenerator.logMsg(fileName, "Timestamp ou extensão ausentes"));
            return false;
        }

        final var timestamp = timestampParts[0];
        final var extension = timestampParts[1];

        if(!dateValidator.isTimestamp(timestamp)){
            log.info(logGenerator.logMsg(fileName,"Timestamp inválido"));
            return false;
        }
        if(!EXPECTED_EXTENSION.equalsIgnoreCase(extension)){
            log.info(logGenerator.logMsg(fileName, "Extensão inválida (esperado: .xml"));
            return false;
        }

        return true;
    }
}
