package com.tatol.videoCamAggregator.service.impl;

import com.tatol.videoCamAggregator.manager.api.AggregatorManager;
import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.service.api.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AggregatorServiceImpl implements AggregatorService {

	@Autowired
	private AggregatorManager aggregatorManager;

	@Override
	public List<CameraFullResponseData> getCameraFullResponseData() {
		return aggregatorManager.getCameraFullResponseData();
	}
}
