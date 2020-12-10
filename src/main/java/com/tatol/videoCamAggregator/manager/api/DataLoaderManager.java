package com.tatol.videoCamAggregator.manager.api;

import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.model.CameraResponseData;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public interface DataLoaderManager {

	List<CameraResponseData> getCameraResponseData();

	void fillResultList(CopyOnWriteArrayList<CameraFullResponseData> list, CameraResponseData cameraResponseData, CountDownLatch countDownLatch);

}
