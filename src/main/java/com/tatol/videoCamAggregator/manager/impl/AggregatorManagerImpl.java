package com.tatol.videoCamAggregator.manager.impl;

import com.tatol.videoCamAggregator.manager.api.AggregatorManager;
import com.tatol.videoCamAggregator.manager.api.DataLoaderManager;
import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.model.CameraResponseData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

@Component
@Log4j2
public class AggregatorManagerImpl implements AggregatorManager {

	@Autowired
	private DataLoaderManager dataLoaderManager;

	@Override
	public List<CameraFullResponseData> getCameraFullResponseData() {
		List<CameraResponseData> cameraResponseData = dataLoaderManager.getCameraResponseData();
		CopyOnWriteArrayList<CameraFullResponseData> resultList = new CopyOnWriteArrayList<>();
		CountDownLatch countDownLatch = new CountDownLatch(cameraResponseData.size());
		try {
			cameraResponseData.forEach(data -> dataLoaderManager.fillResultList(resultList, data, countDownLatch));
			countDownLatch.await();
		} catch (InterruptedException e) {
			log.error(e);
		}
		return resultList;
	}
}
