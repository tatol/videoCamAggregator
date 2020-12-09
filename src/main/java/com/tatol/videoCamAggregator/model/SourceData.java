package com.tatol.videoCamAggregator.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SourceData implements Serializable {
	private static final long serialVersionUID = -7843664933810451313L;

	private UrlType urlType;
	private String videoUrl;
}
