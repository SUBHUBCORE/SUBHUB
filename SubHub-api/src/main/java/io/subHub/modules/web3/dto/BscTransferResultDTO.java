package io.subHub.modules.web3.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BscTransferResultDTO {

    private String hash;

    private String paymentAddress;

    private BigDecimal amount;

    private int status; //0=fail ;1= success




}
