package com.tatol.videoCamAggregator.manager.impl;

import com.google.common.collect.Lists;
import com.tatol.videoCamAggregator.manager.api.DataLoaderManager;
import com.tatol.videoCamAggregator.model.CameraResponseData;
import com.tatol.videoCamAggregator.model.SourceData;
import com.tatol.videoCamAggregator.model.TokenData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
@Log4j2
public class DataLoaderManagerImpl implements DataLoaderManager {

	@Value("${cameraUrl:}")
	private String cameraUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<CameraResponseData> getCameraResponseData() {
		ResponseEntity<CameraResponseData[]> entity = restTemplate.getForEntity(cameraUrl, CameraResponseData[].class);
		CameraResponseData[] entityBody = entity.getBody();
		if (entityBody == null) {
			return new ArrayList<>();
		}
		return Lists.newArrayList(entityBody);
	}

	@Override
	@Async
	public Future<SourceData> getSourceData(String url) {
		return CompletableFuture.completedFuture(restTemplate.getForObject(url, SourceData.class));
	}

	@Override
	@Async
	public Future<TokenData> getTokenData(String url) {
		return CompletableFuture.completedFuture(restTemplate.getForObject(url, TokenData.class));
	}
}
