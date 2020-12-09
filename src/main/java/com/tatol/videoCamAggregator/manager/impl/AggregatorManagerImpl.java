package com.tatol.videoCamAggregator.manager.impl;

import com.tatol.videoCamAggregator.manager.api.AggregatorManager;
import com.tatol.videoCamAggregator.manager.api.DataLoaderManager;
import com.tatol.videoCamAggregator.model.CameraFullFutureResponseData;
import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.model.CameraResponseData;
import com.tatol.videoCamAggregator.model.SourceData;
import com.tatol.videoCamAggregator.model.TokenData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class AggregatorManagerImpl implements AggregatorManager {

	@Autowired
	private DataLoaderManager dataLoaderManager;

	@Override
	public List<CameraFullResponseData> getCameraFullResponseData() {
		List<CameraResponseData> cameraResponseData = dataLoaderManager.getCameraResponseData();
		if (CollectionUtils.isEmpty(cameraResponseData)) {
			return null;
		}
		return cameraResponseData.stream()
				.map(data -> new CameraFullFutureResponseData(
						data.getId(),
						dataLoaderManager.getSourceData(data.getSourceDataUrl()),
						dataLoaderManager.getTokenData(data.getTokenDataUrl())
				))
				.map(data -> new CameraFullResponseData(
						data.getId(),
						getSourceData(data.getSourceDataFuture()).getUrlType(),
						getSourceData(data.getSourceDataFuture()).getVideoUrl(),
						getTokenData(data.getTokenDataFuture()).getValue(),
						getTokenData(data.getTokenDataFuture()).getTtl()
				)).collect(Collectors.toList());
	}

	private SourceData getSourceData(Future<SourceData> sourceDataFuture) {
		try {
			return sourceDataFuture.get();
		} catch (ExecutionException | InterruptedException e) {
			return new SourceData();
		}
	}

	private TokenData getTokenData(Future<TokenData> tokenDataFuture) {
		try {
			return tokenDataFuture.get();
		} catch (ExecutionException | InterruptedException e) {
			return new TokenData();
		}
	}
}
