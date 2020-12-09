package com.tatol.videoCamAggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.Future;

@Getter
@AllArgsConstructor
public class CameraFullFutureResponseData {
	private final Long id;
	private final Future<SourceData> sourceDataFuture;
	private final Future<TokenData> tokenDataFuture;
}
