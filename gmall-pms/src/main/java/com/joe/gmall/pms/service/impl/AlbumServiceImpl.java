package com.joe.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.Album;
import com.joe.gmall.pms.mapper.AlbumMapper;
import com.joe.gmall.pms.service.AlbumService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 相册表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

}
