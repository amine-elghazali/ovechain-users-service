package com.users.service.services;

import com.users.service.dto.createcontract.CreateContractDto;
import com.users.service.entity.Contract;
import com.users.service.entity.Property;
import com.users.service.enums.SmartContractType;
import com.users.service.repository.ContractRepository;
import com.users.service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContractService {
    @Autowired
    StorageService storageService;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    ContractRepository contractRepository;

    public List<Contract> getContracts() {
        return this.contractRepository.findAll();
    }

    public Contract submitContract(CreateContractDto createContractDto, String uid) {
        Property property = new Property();
        property.setUserId(uid);
        property.setCode(createContractDto.getPropertyCode());

        Property candidate  = this.propertyRepository.findOne(Example.of(property)).get();

        if(candidate.getContractId() == null){
            Contract contract = new Contract(
                    UUID.randomUUID().toString(),
                    uid,
                    candidate.getId(),
                    createContractDto.getWallet(),
                    createContractDto.getPrice(),
                    false,
                    SmartContractType.BASIC,
                    createContractDto.getWhiteListWallets(),
                    createContractDto.getBlackListWallets()
            );
            Contract newContract =  this.contractRepository.save(contract);
            candidate.setContractId(newContract.getId());
            this.propertyRepository.save(candidate);
            return newContract;
        }
        else {
            Contract contract = new Contract();
            contract.setPropertyId(property.getId());
            return this.contractRepository.findOne(Example.of(contract)).get();
        }

    }
}
