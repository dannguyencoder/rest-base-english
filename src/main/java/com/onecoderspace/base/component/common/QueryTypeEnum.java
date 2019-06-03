package com.onecoderspace.base.component.common;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Filtering method supported by query conditions")
public enum QueryTypeEnum {
	like,
	equal,
	ne,
	lt,
	lte,
	gt,
	gte
}
