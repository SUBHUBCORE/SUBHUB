package io.subHub.modules.web3.service;

import io.subHub.common.configBean.ConfigBean;
import io.subHub.modules.web3.dto.BscTransferResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class TransactionRecordsService {
    private Logger logger = LoggerFactory.getLogger(TransactionRecordsService.class);

    private final static String transferEventTopic = "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef";

    @Resource
    private ConfigBean configBean;

    public BscTransferResultDTO getBscTransactionRecordsByHash(String hash){
        BscTransferResultDTO bscTransferResultDTO = null;
        Web3j web3j = Web3j.build(new HttpService(configBean.getBscUrl()));
        try {
            TransactionReceipt receipt = web3j.ethGetTransactionReceipt(hash)
                    .send()
                    .getTransactionReceipt()
                    .orElse(null);

            if (receipt != null) {
                String transactionHash = receipt.getTransactionHash();
                BigInteger blockNumber = receipt.getBlockNumber();
                logger.info("hash::"+ receipt.getTransactionHash());
                logger.info("BlockNumber::"+ blockNumber);
                List<Log> logs = receipt.getLogs();
                for (Log log : logs) {
                        List<String> topics = log.getTopics();
                        String topics0 = topics.get(0);
                        logger.info("topics0::"+ topics0);
                        if (topics.size() == 3 && topics0.equalsIgnoreCase(transferEventTopic)) {
                            String from = "0x" + topics.get(1).substring(26);
                            String to = "0x" + topics.get(2).substring(26);
                            BigInteger value = new BigInteger(log.getData().substring(2), 16);
                            BigDecimal valueInToken = new BigDecimal(value).divide(BigDecimal.TEN.pow(18));
                                bscTransferResultDTO = new BscTransferResultDTO();
                                bscTransferResultDTO.setHash(transactionHash);
                                bscTransferResultDTO.setPaymentAddress(from);
                                bscTransferResultDTO.setAmount(valueInToken);
                                logger.info("From::"+ from);
                                logger.info("To::"+ to);
                                logger.info("valueInToken::"+ valueInToken);
                                if (receipt.getStatus().equals("0x1")) {
                                    bscTransferResultDTO.setStatus(1);
                                    logger.info("success::");
                                } else {
                                    bscTransferResultDTO.setStatus(0);
                                    logger.info("fail::");
                                }
                        }
                }
            } else {
                logger.info("Transaction record not found, possibly transaction not confirmed or hash error.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            web3j.shutdown();
        }
        return bscTransferResultDTO;
    }
}