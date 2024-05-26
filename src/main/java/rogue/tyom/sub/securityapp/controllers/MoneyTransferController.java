package rogue.tyom.sub.securityapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogue.tyom.sub.securityapp.dto.TransferDto;
import rogue.tyom.sub.securityapp.services.MoneyTransferService;

@RestController
@RequestMapping("/transfer")
@AllArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    @PostMapping()
    public ResponseEntity<?> transfer(@RequestHeader("Authorization") String authorizationHeader,
                                      @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(moneyTransferService.proceedMoneyTransfer(transferDto, authorizationHeader));
    }
}
