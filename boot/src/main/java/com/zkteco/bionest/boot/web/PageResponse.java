package com.zkteco.bionest.boot.web;

import java.util.List;

import lombok.Data;

@Data
public class PageResponse<T> {

	private Integer pageNumber;

	private Integer pageSize;

	private Long total;

	private List<T> data;

}
