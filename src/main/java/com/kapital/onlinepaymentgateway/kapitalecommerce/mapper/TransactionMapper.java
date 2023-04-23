package com.kapital.onlinepaymentgateway.kapitalecommerce.mapper;

import com.kapital.onlinepaymentgateway.kapitalecommerce.entity.Transaction;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto entityToDto(Transaction transaction);
    List<TransactionDto> entityToDto(List<Transaction> transactions);
    Transaction dtoToEntity(TransactionDto dto);

}
