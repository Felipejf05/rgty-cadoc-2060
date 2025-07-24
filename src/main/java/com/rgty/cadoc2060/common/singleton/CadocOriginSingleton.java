package com.rgty.cadoc2060.common.singleton;

import com.rgty.cadoc2060.domain.CadocOrigin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class CadocOriginSingleton {

    @Getter
    private CadocOrigin fintech001;

    @Getter
    private List<CadocOrigin> originList;
}
