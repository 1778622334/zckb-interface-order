package cn.gydata.zckb.zckbinterfaceorder.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "返回状态")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {

	@ApiModelProperty(value = "返回状态码")
	private int code;

	@ApiModelProperty(value = "返回状态码提示")
	private String desc;
}
