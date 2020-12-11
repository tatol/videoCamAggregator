package com.tatol.videoCamAggregator.manager;

import com.tatol.videoCamAggregator.manager.api.AggregatorManager;
import com.tatol.videoCamAggregator.manager.api.DataLoaderManager;
import com.tatol.videoCamAggregator.manager.impl.AggregatorManagerImpl;
import com.tatol.videoCamAggregator.manager.impl.DataLoaderManagerImpl;
import com.tatol.videoCamAggregator.model.CameraFullResponseData;
import com.tatol.videoCamAggregator.model.CameraResponseData;
import com.tatol.videoCamAggregator.model.SourceData;
import com.tatol.videoCamAggregator.model.TokenData;
import com.tatol.videoCamAggregator.model.UrlType;
import easymock.EasyMockInjectionRunner;
import easymock.Real;
import org.easymock.EasyMock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

@RunWith(EasyMockInjectionRunner.class)
public class AggregatorManagerImplTest {

	private RestTemplate restTemplate = createMock(RestTemplate.class);

	@Real
	private DataLoaderManager dataLoaderManager = new DataLoaderManagerImpl(restTemplate);

	@TestSubject
	private AggregatorManager aggregatorManager = new AggregatorManagerImpl(dataLoaderManager);

	@Test
	public void whenGetCameraResponseDataIsEmpty() {
		reset(restTemplate);
		expect(restTemplate.getForEntity(anyString(), EasyMock.isA(CameraResponseData[].class.getClass())))
				.andReturn(getRestTemplateCameraResponseData(new ArrayList<>())).once();
		replay(restTemplate);

		List<CameraFullResponseData> cameraFullResponseData = aggregatorManager.getCameraFullResponseData();
		Assert.assertEquals(new ArrayList<>(), cameraFullResponseData);
		verify(restTemplate);
	}

	@Test
	public void whenGetCameraResponseDataException() {
		reset(restTemplate);
		expect(restTemplate.getForEntity(anyString(), EasyMock.isA(CameraResponseData[].class.getClass())))
				.andThrow(new RestClientException("")).once();
		replay(restTemplate);

		List<CameraFullResponseData> cameraFullResponseData = aggregatorManager.getCameraFullResponseData();
		Assert.assertEquals(new ArrayList<>(), cameraFullResponseData);
		verify(restTemplate);
	}

	@Test
	public void whenSourceDataEmpty() {
		reset(restTemplate);
		expect(restTemplate.getForEntity(anyString(), EasyMock.isA(CameraResponseData[].class.getClass())))
				.andReturn(
						getRestTemplateCameraResponseData(Collections.singletonList(new CameraResponseData(1L, "test", "test")))
				).once();
		expect(restTemplate.getForObject(anyString(), EasyMock.isA(SourceData.class.getClass()))).andReturn(null).once();
		expect(restTemplate.getForObject(anyString(), EasyMock.isA(TokenData.class.getClass()))).andReturn(new TokenData("111", 33)).once();
		replay(restTemplate);

		List<CameraFullResponseData> cameraFullResponseData = aggregatorManager.getCameraFullResponseData();
		Assert.assertEquals(new ArrayList<>(), cameraFullResponseData);
		verify(restTemplate);
	}

	@Test
	public void whenTokenDataEmpty() {
		reset(restTemplate);
		expect(restTemplate.getForEntity(anyString(), EasyMock.isA(CameraResponseData[].class.getClass())))
				.andReturn(
						getRestTemplateCameraResponseData(Collections.singletonList(new CameraResponseData(1L, "test", "test")))
				).once();
		expect(restTemplate.getForObject(anyString(), EasyMock.isA(SourceData.class.getClass()))).andReturn(new SourceData(UrlType.ARCHIVE, "22")).once();
		expect(restTemplate.getForObject(anyString(), EasyMock.isA(TokenData.class.getClass()))).andReturn(null).once();
		replay(restTemplate);

		List<CameraFullResponseData> cameraFullResponseData = aggregatorManager.getCameraFullResponseData();
		Assert.assertEquals(new ArrayList<>(), cameraFullResponseData);
		verify(restTemplate);
	}

	@Test
	public void whenTokenDataException() {
		reset(restTemplate);
		expect(restTemplate.getForEntity(anyString(), EasyMock.isA(CameraResponseData[].class.getClass())))
				.andReturn(
						getRestTemplateCameraResponseData(Collections.singletonList(new CameraResponseData(1L, "test", "test")))
				).once();
		expect(restTemplate.getForObject(anyString(), EasyMock.isA(SourceData.class.getClass()))).andReturn(new SourceData(UrlType.ARCHIVE, "22")).once();
		expect(restTemplate.getForObject(anyString(), EasyMock.isA(TokenData.class.getClass()))).andThrow(new RestClientException("")).once();
		replay(restTemplate);

		List<CameraFullResponseData> cameraFullResponseData = aggregatorManager.getCameraFullResponseData();
		Assert.assertEquals(new ArrayList<>(), cameraFullResponseData);
		verify(restTemplate);
	}

	@Test
	public void whenSuccess() {
		List<CameraResponseData> dataList = Arrays.asList(
				new CameraResponseData(1L, "test1", "test11"),
				new CameraResponseData(2L, "test2", "test22")
		);
		reset(restTemplate);
		expect(restTemplate.getForEntity(anyString(), EasyMock.isA(CameraResponseData[].class.getClass())))
				.andReturn(getRestTemplateCameraResponseData(dataList)).once();
		expect(restTemplate.getForObject("test1", SourceData.class)).andReturn(new SourceData(UrlType.ARCHIVE, "video1")).once();
		expect(restTemplate.getForObject("test11", TokenData.class)).andReturn(new TokenData("token1", 111)).once();
		expect(restTemplate.getForObject("test2", SourceData.class)).andReturn(new SourceData(UrlType.LIVE, "video2")).once();
		expect(restTemplate.getForObject("test22", TokenData.class)).andReturn(new TokenData("token2", 222)).once();
		replay(restTemplate);

		List<CameraFullResponseData> resultList = Arrays.asList(
				new CameraFullResponseData(1L, UrlType.ARCHIVE, "video1", "token1", 111),
				new CameraFullResponseData(2L, UrlType.LIVE, "video2", "token2", 222)
		);
		List<CameraFullResponseData> cameraFullResponseData = aggregatorManager.getCameraFullResponseData();
		Assert.assertEquals(resultList.get(0), cameraFullResponseData.get(0));
		Assert.assertEquals(resultList.get(1), cameraFullResponseData.get(1));
		verify(restTemplate);
	}


	private ResponseEntity getRestTemplateCameraResponseData(List<CameraResponseData> list) {
		return ResponseEntity.ok(list.toArray(new CameraResponseData[0]));
	}

}
