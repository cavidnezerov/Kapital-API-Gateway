package com.kapital.onlinepaymentgateway.kapitalecommerce.service;

import com.kapital.onlinepaymentgateway.kapitalecommerce.error.DataNotFoundException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.response.ResponseMessage;
import com.kapital.onlinepaymentgateway.kapitalecommerce.mapper.TransactionMapper;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.TransactionDto;
import com.kapital.onlinepaymentgateway.kapitalecommerce.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionDto save(TransactionDto transaction) {
        return TransactionMapper.INSTANCE
                .entityToDto(transactionRepository
                        .save(TransactionMapper.INSTANCE
                                .dtoToEntity(transaction)));
    }

    public TransactionDto findById(Long id) {
        return transactionRepository.findById(id)
                .map(TransactionMapper.INSTANCE::entityToDto)
                .orElseThrow(() -> new DataNotFoundException(ResponseMessage.TRANSACTION_NOT_FOUND.formatted(id)));
    }

    public List<TransactionDto> get() {
        return transactionMapper.entityToDto(transactionRepository.findAll());
    }
}
