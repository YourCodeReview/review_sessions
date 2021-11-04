package com.rufus.bumblebee.services.generators;

import com.google.gson.Gson;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.services.dto.KafkaDto;
import com.rufus.bumblebee.services.dto.TestDataDto;
import com.rufus.bumblebee.services.interfaces.KafkaService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService<List<TestDataDto>> {

    private final KafkaTemplate<String, String> template;
    private final Gson gson = new Gson();
    private final NewTopic topic;

    @Autowired
    public KafkaServiceImpl(KafkaTemplate<String, String> template, NewTopic topic) {
        this.template = template;
        this.topic = topic;
    }

    public void sendTestData(List<TestDataDto> data, Container container) {
        KafkaDto dto = new KafkaDto();
        dto.setCuid(container.getCuid().toString());
        dto.setContainerName(container.getName());
        dto.setHistoryOn(container.getHistoryOn());
        dto.setReportType(container.getType());
        dto.setData(data);
        template.send(topic.name(), gson.toJson(dto));
    }
}
