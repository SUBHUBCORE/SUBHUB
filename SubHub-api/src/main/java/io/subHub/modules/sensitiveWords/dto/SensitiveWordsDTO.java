package io.subHub.modules.sensitiveWords.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class SensitiveWordsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private String category;

	private String terms;

	private Date createDt;



}