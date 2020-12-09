package com.tatol.videoCamAggregator.manager.api;

import com.tatol.videoCamAggregator.model.CameraResponseData;
import com.tatol.videoCamAggregator.model.SourceData;
import com.tatol.videoCamAggregator.model.TokenData;

import java.util.List;
import java.util.concurrent.Future;

public interface DataLoaderManager {

	List<CameraResponseData> getCameraResponseData();

	Future<SourceData> getSourceData(String url);

	Future<TokenData> getTokenData(String url);

}
