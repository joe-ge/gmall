package com.joe.gmall.admin.oms.controller;



import com.joe.gmall.admin.oms.component.OssComponent;
import com.joe.gmall.to.CommonResult;
import com.joe.gmall.to.OssPolicyResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 安全起见，应该利用跨域拦截非前端发过来的非法请求@CrossOrigin(origins = "")
 * 1前端搜索leifengyang地址修改为自己的阿里云地址
 * 2applications的配置改为自己的
 * 3阿里云的跨域设置打开
 * Oss相关操作接口
 */
@CrossOrigin
@Controller
@Api(tags = "OssController",description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
	@Autowired
	private OssComponent ossComponent;

	@ApiOperation(value = "oss上传签名生成")
	@GetMapping(value = "/policy")
	@ResponseBody
	public Object policy() {
		OssPolicyResult result = ossComponent.policy();
		return new CommonResult().success(result);
	}

}
