package com.tatol.videoCamAggregator;

import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.service.api.AggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AggregatorController {

	private final AggregatorService aggregatorService;

	@RequestMapping(value = "/cameras", method = RequestMethod.GET)
	public List<CameraFullResponseData> getCameraFullResponseData() {
		return aggregatorService.getCameraFullResponseData();
	}

}
