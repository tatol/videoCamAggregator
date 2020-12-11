package com.tatol.videoCamAggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class CameraFullResponseData implements Serializable {
	private static final long serialVersionUID = 4922907110141090095L;

	private final Long id;
	private final UrlType urlType;
	private final String videoUrl;
	private final String value;
	private final Integer ttl;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CameraFullResponseData that = (CameraFullResponseData) o;
		return Objects.equals(getId(), that.getId()) &&
				getUrlType() == that.getUrlType() &&
				Objects.equals(getVideoUrl(), that.getVideoUrl()) &&
				Objects.equals(getValue(), that.getValue()) &&
				Objects.equals(getTtl(), that.getTtl());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUrlType(), getVideoUrl(), getValue(), getTtl());
	}
}
