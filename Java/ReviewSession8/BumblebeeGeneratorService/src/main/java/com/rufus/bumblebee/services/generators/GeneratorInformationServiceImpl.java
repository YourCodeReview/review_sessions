package com.rufus.bumblebee.services.generators;

import com.rufus.bumblebee.services.dto.GeneratorDto;
import com.rufus.bumblebee.services.dto.GeneratorParametersDto;
import com.rufus.bumblebee.generators.annotation.GeneratorDescription;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;
import com.rufus.bumblebee.generators.annotation.InformationAnnotationHandler;
import com.rufus.bumblebee.services.interfaces.GeneratorInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeneratorInformationServiceImpl implements GeneratorInformationService<List<GeneratorDto>> {

    private final InformationAnnotationHandler<Map<GeneratorDescription, List<GeneratorParameter>>> handler;

    @Autowired
    public GeneratorInformationServiceImpl(InformationAnnotationHandler<Map<GeneratorDescription, List<GeneratorParameter>>> handler) {
        this.handler = handler;
    }

    @Override
    public List<GeneratorDto> getInformation() {
        List<GeneratorDto> generatorInfo = new ArrayList<>();
        Map<GeneratorDescription, List<GeneratorParameter>> map = handler.getInformation();
        for (Map.Entry<GeneratorDescription, List<GeneratorParameter>> entry : map.entrySet()) {
            generatorInfo.add(new GeneratorDto(
                    entry.getKey().generatorName(),
                    entry.getKey().description(),
                    entry.getValue().stream()
                            .map(s -> new GeneratorParametersDto(s.name(), s.description()))
                            .collect(Collectors.toList())));
        }
        return generatorInfo;
    }
}
