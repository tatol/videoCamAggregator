package com.tatol.videoCamAggregator.service.api;

import com.tatol.videoCamAggregator.model.CameraFullResponseData;

import java.util.List;

public interface AggregatorService {

	List<CameraFullResponseData> getCameraFullResponseData();

}
