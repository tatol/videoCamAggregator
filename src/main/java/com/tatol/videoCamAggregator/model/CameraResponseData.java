package com.tatol.videoCamAggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CameraResponseData implements Serializable {
	private static final long serialVersionUID = 4085700534431206960L;

	private Long id;
	private String sourceDataUrl;
	private String tokenDataUrl;

}
