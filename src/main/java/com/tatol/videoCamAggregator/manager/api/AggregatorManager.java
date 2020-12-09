package com.tatol.videoCamAggregator.manager.api;

import com.tatol.videoCamAggregator.model.CameraFullResponseData;

import java.util.List;

public interface AggregatorManager {

	List<CameraFullResponseData> getCameraFullResponseData();

}
