package com.tatol.videoCamAggregator;

import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.service.api.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregatorController {

	@Autowired
	private AggregatorService aggregatorService;

	@RequestMapping(value = "/cameras", method = RequestMethod.GET)
	public List<CameraFullResponseData> getCameraFullResponseData() {
		return aggregatorService.getCameraFullResponseData();
	}

}
