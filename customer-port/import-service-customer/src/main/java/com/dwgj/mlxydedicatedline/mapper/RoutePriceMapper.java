package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.RoutePrice;
import com.dwgj.mlxydedicatedline.vo.route.BigPackagePriceVo;
import com.dwgj.mlxydedicatedline.vo.route.SmallPackagePriceVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * RoutePriceMapper继承基类
 * @author lrq
 * 2019年11月4日16:59:41
 */
@Repository
public interface RoutePriceMapper extends MyBatisBaseDao<RoutePrice, Integer> {

    /**
     * 根据路线id查询相关价格
     * @param routeId
     * @return
     */
    List<RoutePrice> fingByRouteId(int routeId);

    /**
     * 根据重量查询
     * @param weight
     * @param routeId
     * @return
     */
    RoutePrice findByWeight(@Param("routeId") int routeId, @Param("weight") double weight);

    /**
     * 根据体积查询
     * @param vol
     * @param routeId
     * @return
     */
    RoutePrice findByVol(@Param("routeId") int routeId, @Param("vol") double vol);

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
     * 查询小包裹价格集合
     * @param routeId
     * @return
     */
    List<RoutePrice> findSmallPackByRouteId(int routeId);

    /**
     * 获取小包裹价格
     * @param routeId
     * @param weight
     * @return
     */
    RoutePrice findSmallPackPrice(@Param("routeId") int routeId, @Param("weight") double weight);

    /**
     * 查询 空运 按照KG  价格集合
     * @param routeId
     * @return
     */
    List<RoutePrice> findAirSmallPackPrice(int routeId);

    List<RoutePrice> findAirSmallPackPriceDesc(int routeId);

    List<RoutePrice> findSeaSmallPackPrice(int routeId);

    List<RoutePrice> findSeaBigPackPrice(int routeId);

    RoutePrice findBySeaSmallPrice(@Param("routeId")int routeId, @Param("weight") Double weight);

    RoutePrice findSmallPackPriceByWeight(@Param("routeId")int routeId,@Param("weight") Double weight);

    List<RoutePrice> findSeaSmallPackPriceDesc(Integer id);


    List<SmallPackagePriceVo> findSmallPackageByRouteId(Integer routeId);

    List<BigPackagePriceVo> findBigPackageByRouteId(Integer routeId);

    /**
     * 根据 路线 Id 获取价格详情
     * @param routeId
     * @return
     */
    List<RoutePrice> findByRouteId(@Param("routeId")int routeId, @Param("routeType")int routeType);

    /**
     * 活动路线查询
     * 排序方式：起始重量
     * @param routeId
     * @return
     */
    List<RoutePrice> findByRouteIdOfActivity(@Param("routeId")int routeId);

    /**
     * 获取所有的 有效价格 集合
     * @return
     */
    List<RoutePrice> getAllPrice();
}
