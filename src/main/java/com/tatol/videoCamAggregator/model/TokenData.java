package com.tatol.videoCamAggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenData implements Serializable {
	private static final long serialVersionUID = -4614353900207608556L;

	private String value;
	private Integer ttl;
}
