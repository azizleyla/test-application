package com.unibank.unitech.controller;

import com.unibank.unitech.dto.request.TransferRequestDto;
import com.unibank.unitech.dto.response.TransferResponseDto;
import com.unibank.unitech.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.unibank.unitech.constants.GeneralConstants.AUTHORIZATION;

@RestController
@RequestMapping(value = "/transfer")
@RequiredArgsConstructor
@Validated
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(@RequestBody @Valid TransferRequestDto transferRequest,
                                                        @RequestHeader(name = AUTHORIZATION) String authentication) {
        TransferResponseDto response = transferService.doTransfer(transferRequest, authentication);
        return ResponseEntity.ok(response);
    }

}
