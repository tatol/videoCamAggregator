package com.tatol.videoCamAggregator.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TokenData implements Serializable {
	private static final long serialVersionUID = -4614353900207608556L;

	private String value;
	private Integer ttl;
}
