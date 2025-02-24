package com.genersoft.iot.vmp.vmanager.gb28181.gbStream;

import com.genersoft.iot.vmp.gb28181.bean.GbStream;
import com.genersoft.iot.vmp.storager.IVideoManagerStorager;
import com.genersoft.iot.vmp.vmanager.gb28181.gbStream.bean.GbStreamParam;
import com.genersoft.iot.vmp.service.IGbStreamService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "视频流关联到级联平台")
@CrossOrigin
@RestController
@RequestMapping("/api/gbStream")
public class GbStreamController {

    private final static Logger logger = LoggerFactory.getLogger(GbStreamController.class);

    @Autowired
    private IGbStreamService gbStreamService;

    @Autowired
    private IVideoManagerStorager storager;


    /**
     * 查询国标通道
     * @param page 当前页
     * @param count 每页条数
     * @return
     */
    @ApiOperation("查询国标通道")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true , dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "count", value = "每页条数", required = true , dataTypeClass = Integer.class),
    })
    @GetMapping(value = "/list")
    @ResponseBody
    public PageInfo<GbStream> list(@RequestParam(required = false)Integer page,
                                   @RequestParam(required = false)Integer count){

        return gbStreamService.getAll(page, count);
    }


    /**
     * 移除国标关联
     * @param gbStreamParam
     * @return
     */
    @ApiOperation("移除国标关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gbStreamParam", value = "GbStreamParam", required = true,
                    dataTypeClass = GbStreamParam.class),
    })
    @DeleteMapping(value = "/del")
    @ResponseBody
    public Object del(@RequestBody GbStreamParam gbStreamParam){
        if (gbStreamService.delPlatformInfo(gbStreamParam.getGbStreams())) {
            return "success";
        }else {
            return "fail";
        }

    }

    /**
     * 保存国标关联
     * @param gbStreamParam
     * @return
     */
    @ApiOperation("保存国标关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gbStreamParam", value = "GbStreamParam", required = true, dataTypeClass = GbStreamParam.class),
    })
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestBody GbStreamParam gbStreamParam){
        if (gbStreamService.addPlatformInfo(gbStreamParam.getGbStreams(), gbStreamParam.getPlatformId(), gbStreamParam.getCatalogId())) {
            return "success";
        }else {
            return "fail";
        }
    }
}
