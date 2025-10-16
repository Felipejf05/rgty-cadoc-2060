package com.rgty.cadoc2060.common.singleton;

import com.rgty.cadoc2060.domain.CadocStatus;
import com.rgty.cadoc2060.enums.StatusEnum;
import com.rgty.cadoc2060.repository.CadocStatusRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rgty.cadoc2060.enums.StatusEnum.CADOC2060_FILE_SPLIT;
import static com.rgty.cadoc2060.enums.StatusEnum.CADOC2060_RECEIVED;
import static com.rgty.cadoc2060.enums.StatusEnum.CADOC2060_UPLOAD_ERROR;
import static com.rgty.cadoc2060.enums.StatusEnum.CADOC2060_VALIDATION_ERROR;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class CadocStatusSingleton {

    @Getter
    private CadocStatus received;

    @Getter
    private CadocStatus uploadError;

    @Getter
    private CadocStatus validationError;

    //    @Getter
//    private CadocStatus copiedToSmb;

    @Getter
    private CadocStatus fileSplit;

    @Getter
    private List<CadocStatus> statusList;

    private final CadocStatusRepository cadocStatusRepository;

    @PostConstruct
    public void init(){
        statusList = cadocStatusRepository.findAll();

        received = getStatus(CADOC2060_RECEIVED);
        uploadError = getStatus(CADOC2060_UPLOAD_ERROR);
        validationError = getStatus(CADOC2060_VALIDATION_ERROR);
        fileSplit = getStatus(CADOC2060_FILE_SPLIT);

    }

    private CadocStatus getStatus(final StatusEnum statusEnum){
        return statusList.stream()
                .filter(s -> s.getNemotecnico().equals(statusEnum.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CadocStatus não encontrado"));
    }
}
