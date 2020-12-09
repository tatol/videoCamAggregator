package com.tatol.videoCamAggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CameraFullResponseData implements Serializable {
	private static final long serialVersionUID = 4922907110141090095L;

	private final Long id;
	private final UrlType urlType;
	private final String videoUrl;
	private final String value;
	private final Integer ttl;
}
