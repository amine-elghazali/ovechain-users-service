package com.users.service.services;

import com.users.service.dto.CreatePropertyDto;
import com.users.service.entity.Property;
import com.users.service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class PropertyService {
    @Autowired
    StorageService storageService;

    @Autowired
    PropertyRepository propertyRepository;


    public Property registerProperty(CreatePropertyDto createPropertyDto, String uid) throws IOException, ExecutionException, InterruptedException {
        this.storageService.downloadPropertyFolder(uid,createPropertyDto.getCode(), 0);
        Property property;
        property = new Property(
                UUID.randomUUID().toString(),
                uid,
                createPropertyDto.getCode(),
                createPropertyDto.getTitle(),
                createPropertyDto.getDescription(),
                createPropertyDto.getAddress(),
                false
        );

        return this.propertyRepository.save(property);
    }
}
