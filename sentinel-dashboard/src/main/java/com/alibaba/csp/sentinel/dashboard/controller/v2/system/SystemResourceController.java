package com.alibaba.csp.sentinel.dashboard.controller.v2.system;

import com.alibaba.csp.sentinel.dashboard.auth.AuthAction;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.vo.result.ResourceResultVO;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/v2/system/resource")
public class SystemResourceController {


    @GetMapping("/getResources.json")
    @AuthAction(AuthService.PrivilegeType.READ_RULE)
    public Result<List<ResourceResultVO>> getResources(HttpServletRequest request) {
        List<ResourceResultVO> resourceList = Lists.newArrayList();
        ResourceResultVO resourceResult = new ResourceResultVO();
        resourceResult.setResourceName("控制台");
        resourceResult.setResourceUrl("");
        resourceResult.setAppType(0);
        resourceResult.setShown(true);
        resourceList.add(resourceResult);
        return Result.ofSuccess(resourceList);
    }
}
