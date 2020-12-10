package com.tatol.videoCamAggregator.manager.impl;

import com.google.common.collect.Lists;
import com.tatol.videoCamAggregator.manager.api.DataLoaderManager;
import com.tatol.videoCamAggregator.model.CameraFullResponseData;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

@Component
@Log4j2
public class DataLoaderManagerImpl implements DataLoaderManager {

	@Value("${cameraUrl:}")
	private String cameraUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<CameraResponseData> getCameraResponseData() {
		CameraResponseData[] entityBody;
		try {
			ResponseEntity<CameraResponseData[]> entity = restTemplate.getForEntity(cameraUrl, CameraResponseData[].class);
			entityBody = entity.getBody();
			return entityBody != null ? Lists.newArrayList(entityBody) : new ArrayList<>();
		} catch (Exception e) {
			log.error(e);
		}
		return new ArrayList<>();
	}

	@Override
	@Async
	public void fillResultList(CopyOnWriteArrayList<CameraFullResponseData> list, CameraResponseData data, CountDownLatch countDownLatch) {
		try {
			SourceData sourceData = restTemplate.getForObject(data.getSourceDataUrl(), SourceData.class);
			TokenData tokenData = restTemplate.getForObject(data.getTokenDataUrl(), TokenData.class);
			if (sourceData != null && tokenData != null) {
				list.add(new CameraFullResponseData(data.getId(), sourceData.getUrlType(), sourceData.getVideoUrl(), tokenData.getValue(), tokenData.getTtl()));
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			countDownLatch.countDown();
		}
	}
}
