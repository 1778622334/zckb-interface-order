package cn.gydata.zckb.zckbinterfaceorder.util;


 
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "返回信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "返回状态")
	private Status status;

	@ApiModelProperty(value = "返回数据")
	private T returndata;

	@ApiModelProperty(value = "当前系统时间")
	private String sysTime;

	public ResultData(Status status,T returndata){
		this.status = status;
		this.returndata = returndata;
		this.sysTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
 }
