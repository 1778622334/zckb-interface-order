package cn.gydata.zckb.zckbinterfaceorder.util;


import cn.gydata.zckb.zckbinterfaceorder.enums.CodeEnum;

/**
 * 处理返回值
 */
public class ResultUtil {

	public static <E> ResultData<E> success(E o) {
		Status status = Status.builder().code(CodeEnum.SUCCESS.getCode()).desc(CodeEnum.SUCCESS.getMessage()).build();
		return new ResultData<E>(status,o);
	}

	/** 成功  */
	public static ResultData<Object> success(){
		return success(null);
	}
	
	/** 失败 */
	public static ResultData<Object> error(Integer code,String message){
		Status status = Status.builder().code(code).desc(message).build();
		return  ResultData.builder().status(status).build();
	}

	/** 失败 */
	public static ResultData<Object> error(Integer code,String message,Object object){
		ResultData<Object> resultData = new ResultData<Object>();
		Status status = new Status();
		status.setCode(code);
		status.setDesc(message);
		resultData.setStatus(status);
		resultData.setReturndata(object);
		return resultData;
	}
	
	/** 系统错误 */
	public static ResultData<Object> error(){
		return error(CodeEnum.ERROR.getCode(),CodeEnum.ERROR.getMessage());
	}
	
	/** 公共错误 */
	public static ResultData<Object> error(CodeEnum codeEnum){
		return error(codeEnum.getCode(),codeEnum.getMessage());
	}

	/** 验证失败 */
	public static ResultData<Object> validateError(String message){
		ResultData<Object> resultData = new ResultData<Object>();
		Status status = new Status();
		status.setCode(-2);
		status.setDesc(message);
		resultData.setStatus(status);
		return resultData;
	}

	public static ResultData<Object> loginError(){return error(CodeEnum.LOGINERROR);}

}
