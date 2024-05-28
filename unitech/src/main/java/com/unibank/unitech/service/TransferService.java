package com.unibank.unitech.service;

import com.unibank.unitech.dto.request.TransferRequestDto;
import com.unibank.unitech.dto.response.TransferResponseDto;

public interface TransferService {

    TransferResponseDto doTransfer(TransferRequestDto transferRequest, String authentication);

}
