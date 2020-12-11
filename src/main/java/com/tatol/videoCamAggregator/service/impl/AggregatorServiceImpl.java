package com.tatol.videoCamAggregator.service.impl;

import com.tatol.videoCamAggregator.manager.api.AggregatorManager;
import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.service.api.AggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AggregatorServiceImpl implements AggregatorService {

	private final AggregatorManager aggregatorManager;

	@Override
	public List<CameraFullResponseData> getCameraFullResponseData() {
		return aggregatorManager.getCameraFullResponseData();
	}
}
