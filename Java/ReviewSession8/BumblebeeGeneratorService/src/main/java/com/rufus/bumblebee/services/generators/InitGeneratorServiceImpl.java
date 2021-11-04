package com.rufus.bumblebee.services.generators;

import com.rufus.bumblebee.controllers.requests.GeneratorRequest;
import com.rufus.bumblebee.generators.DataGenerator;
import com.rufus.bumblebee.generators.GeneratorInformation;
import com.rufus.bumblebee.generators.annotation.GeneratorDescription;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;
import com.rufus.bumblebee.generators.annotation.InformationAnnotationHandler;
import com.rufus.bumblebee.repository.ContainerRepository;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.services.interfaces.GeneratorParametersService;
import com.rufus.bumblebee.services.interfaces.DataGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rufus.bumblebee.BumblebeeGeneratorService.*;
import static com.rufus.bumblebee.services.dto.ContainerStatus.*;

@Service
public class InitGeneratorServiceImpl extends BaseInitGeneratorService<GeneratorRequest> {

    private static final Logger log = LoggerFactory.getLogger(InitGeneratorServiceImpl.class);

    private final ContainerRepository containerRepository;
    private final DataGenerationService dataGenerationService;
    private final GeneratorParametersService generatorParametersService;

    public InitGeneratorServiceImpl(ContainerRepository containerRepository,
                                    DataGenerationService dataGenerationService,
                                    GeneratorParametersService generatorParametersService,
                                    ApplicationContext context,
                                    InformationAnnotationHandler<Map<GeneratorDescription, List<GeneratorParameter>>> handler) {
        super(context, handler);
        this.containerRepository = containerRepository;
        this.dataGenerationService = dataGenerationService;
        this.generatorParametersService = generatorParametersService;
    }

    @Override
    public Map<String, String> initGenerators(GeneratorRequest request) throws Exception {
        validateGeneratorsRequest(request);
        Container container = containerRepository.getContainerByCuid(request.getCuid());

        if (GENERATION_COMPLETED.equals(container.getStatus()) || PREPARATION_FOR_GENERATION.equals(container.getStatus())) {
            throw new Exception("Container status exception for status: " + container.getStatus());
        }
        List<DataGenerator> generators = new ArrayList<>(request.getGeneratorInfo().size());

        for (GeneratorInformation information : request.getGeneratorInfo()) {
            try {
                DataGenerator generator = (DataGenerator) getGeneratorByName(information.getGeneratorName());
                generatorParametersService.setParameters(getFields(generator.getClass()), information.getValues(), generator);
                generators.add(generator);
            } catch (Exception exception) {
                container.setStatus(GENERATION_ERROR);
                log.error("Error in the process of generating generators", exception);
                throw exception;
            }
        }
        container.setStatus(PREPARATION_FOR_GENERATION);

        dataGenerationService.generateTestData(generators, containerRepository.save(container));

        return new HashMap<String, String>() {{
            put(KEY_CONTAINER_NAME, container.getName());
            put(KEY_UID, container.getCuid().toString());
            put(KEY_STATUS, container.getStatus().name());
            put(KEY_GENERATORS_SIZE, String.valueOf(generators.size()));
        }};
    }
}
