package com.example.warehouse.mapper.routePrice;

import com.example.warehouse.entity.RoutePrice;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.route.PackageRoutePriceVo;
import com.example.warehouse.vo.route.SmallPackageRoutePriceVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoutePriceMapper继承基类
 * @author lrq
 * 2019年11月4日16:59:41
 */
@Repository
public interface RoutePriceMapper extends MyBatisBaseDao<RoutePrice, Integer> {

    /**
     * 查询相关路线的所有价格
     * @param routeId
     * @return
     */
    List<RoutePrice> findAll(int routeId);


    /**
     * 根据 路线 Id 获取价格详情
     * @param routeId
     * @return
     */
    List<RoutePrice> findByRouteId(@Param("routeId")int routeId, @Param("routeType")int routeType);

    /**
     * 根据路线id查询相关价格(小包裹)
     * @param routeId
     * @return
     */
    List<SmallPackageRoutePriceVo> findSmallPackageByRouteId(int routeId);

    /**
     * 根据路线id查询相关价格(大包裹)
     * @param routeId
     * @return
     */
    List<PackageRoutePriceVo> findBigPackageByRouteId(int routeId);

    /**
     * 根据重量查询
     * @param weight
     * @param routeId
     * @return
     */
    RoutePrice findByWeight(@Param("routeId") int routeId, @Param("weight")double weight);

    /**
     * 根据体积查询
     * @param vol
     * @param routeId
     * @return
     */
    RoutePrice findByVol(@Param("routeId")int routeId, @Param("vol")double vol);

    /**
     * 根据 价格代码 查询
     * @param priceNo
     * @return
     */
    RoutePrice findByPriceNo(String priceNo);

    /**
     * 根据 id 删除对应 路线 价格，逻辑删除
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List ids);

    /**
     * 根据 priceNo 删除对应 路线 价格，逻辑删除
     * @param priceNo
     */
    void deleteByPriceNo(String priceNo);

    /**
     * 查询 空运 小包裹 实例 正序
     * @return
     */
    List<RoutePrice> findAirSmallPackPrice(int routeId);
    /**
     * 查询 空运 小包裹实例 倒序
     * @return
     */
    List<RoutePrice> findAirSmallPackPriceDesc(int routeId);

    /**
     * 查询 空运 大包裹实例
     * @return
     */
    List<RoutePrice> findAirBigPackPrice(int routeId);

    RoutePrice findSmallPackPriceByWeight(@Param("routeId")int routeId, @Param("weight")Double weight);
    /**
     * 查询 海运 小包裹的实例 正序
     * @return
     */
    List<RoutePrice> findSeaSmallPackPrice(int routeId);
    /**
     * 查询 海运 小包裹的实例 倒序
     * @return
     */
    List<RoutePrice> findSeaSmallPackPriceDesc(int routeId);


    RoutePrice findBySeaSmallPrice(@Param("routeId")int routeId, @Param("weight")Double weight);

    List<RoutePrice> findByRouteIdAndRouteType(@Param("routeId")int routeId, @Param("routeType")int routeType);

    List<RoutePrice> findByRouteIdList(@Param("routeIds") List<Integer> routeIds);
}
