package com.example.warehouse.common;

import com.example.warehouse.entity.RoutePrice;
import com.example.warehouse.entity.TransportationRoute;
import com.example.warehouse.mapper.routePrice.RoutePriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 各种计算方法
 * @author lrq
 * 2019年11月8日13:40:07
 */
@Configuration
public class CalculationUtils {

    @Autowired
    private RoutePriceMapper routePriceMapper;

    /**
     * 重量进位
     * @param weight
     * @param type
     * @return
     */
    public Double weightCarry(double weight, int type) {
        double settlementWeight;
        if (type == 1) {
            BigDecimal bd = new BigDecimal(weight);
            bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
            String str = String.valueOf(bd);
            String ing = str.substring(str.length()-2);
            str = str.substring(0,str.length()-3);
            if(Integer.parseInt(ing) > 0 && Integer.parseInt(ing) < 50 ){
                ing = "50";
                settlementWeight = Double.valueOf(str + "." + ing);
            }else if(Integer.parseInt(ing) > 50){
                ing = "00";

                settlementWeight = Double.valueOf((Integer.parseInt(str) + 1) + "." + ing);
            }else {
                settlementWeight = weight;
            }

        } else {
            settlementWeight = Math.ceil(weight);
        }
        return settlementWeight;
    }

    public Double volCarry(double vol, double weight) {
        double settlementVol;
        double w = Math.ceil(weight/500);
        // 当体积不足 0.5m³ 时，按照 0.5m³ 计算
        BigDecimal b = new BigDecimal(w);
        BigDecimal volb = new BigDecimal(vol);


        w = b.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        vol = volb.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        // 当体积不足 0.5m³ 时，按照 0.5m³ 计算
        if(vol < 0.5){
            settlementVol = 0.5;
        }else
            // 1m³ 的体积上限为 500kg ，当 重量 除以 500 大于 体积 时，取 重量/500 的体积数
            if(vol < w){
                settlementVol = w;
            }else {
                settlementVol = vol;
            }

        return settlementVol;
    }

    /**
     * 空运 单件物品 重量上限
     * @param upperLimitWeight
     * @param weight
     * @return
     */
    public boolean upperLimitOfWeight(double upperLimitWeight, double weight){
        if(weight > upperLimitWeight){
            return false;
        } else {
            return true;
        }
    }

    /**
     * 单件物品边长上限
     * @param firstLength
     * @param secondLenght
     * @param length
     * @param width
     * @param height
     * @return
     */
    public boolean upperLimitOfLength(double firstLength, double secondLenght, double length, double width, double height){

        double[] arr = {length, width, height};
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        if(arr[0] > firstLength && arr[1] > secondLenght){
            return false;
        }else if(arr[1] > secondLenght){
            return false;
        }
        return true;
    }

    /**
     * 根据选定的路线计算KG的价格，这条渠道价钱是KG计算的，route_type=1
     * @param priceList
     * @param route
     * @param kg
     * @param vol
     * @param isPin
     * @param isCalculateVolWeight
     * @return
     */
    public Map<String,Object> countKGMoney(List<RoutePrice> priceList, TransportationRoute route, double kg, double vol, boolean isPin, boolean isCalculateVolWeight){
        Map<String,Object> resultMap = new HashMap<>();
        // 1、 空运  2、海运  后续做路线中取routeType的值
        if(priceList == null || priceList.isEmpty()){
            priceList = routePriceMapper.findByRouteId(route.getId(),1);
        }

        RoutePrice routePrice = null; // 最终价格段

        // 计算KG落在哪个区间内
        if(!priceList.isEmpty()) {
            // 如果是拼团渠道，则不用计算价格区间
            if(!isPin){
                // 计算价格区间
                routePrice = getCurrentRouteByKg(kg,priceList);

                if( priceList.get(priceList.size() - 1).getEndWeight() < kg){
                    System.out.println("重量超出最大限度，返回");
                    return null;
                }

            }else {
                routePrice = priceList.get(0);
            }




            // 获取进位规则,重量进位类型 1、大于0进0.5   2、大于0进1  3、不进位
            // 当 进位规则重量 AfterWeightCarry 不为空 和 货物总重量 大于等于 进位规则重量 AfterWeightCarry时，进位规则取AfterWeightCarry的进位规则
            int type;
            if(route.getAfterWeightCarry() != null && kg >= route.getAfterWeightCarry()){
                type = route.getAfterWeightCarryType();
            }else {
                type = route.getBeforeWeightCarryType();
            }

            double start = routePrice.getStartWeight(); // 开始区间值
            double finalKG = weightCarry(start, type, kg); // 得到最终进位的KG

            BigDecimal price = new BigDecimal(0);

            // 计算类型，1、KG对应总运费，直接取值   2、KG对应单价，则需要乘以单价
            int priceType = routePrice.getPackageType();
            if (priceType == 1) {
                price = price.add(routePrice.getPrice());
            }
            else {
                if( routePrice.getStartWeight().compareTo(routePrice.getEndWeight()) == 0 ){
                    // 如果起始重量等于结尾重量，那么判定它对应的价格为总运费
                    price = price.add(routePrice.getPrice());
                }else {
                    // 如果起始重量不等于结尾重量，则对应单价价钱
                    price = price.add(BigDecimal.valueOf(finalKG).multiply(routePrice.getPrice()));
                }
            }
            /**
             * 计算重量体积 : 体积 * （100*100*100/volConversion）
             */
            // 四舍五入，如果是6000，则是167,2020-07-06 起，直接使用立方除以分泡
            //            double conversion = Math.ceil((100*100*100) / route.getVolConversion());


            // 需要计算体积重的时候
            if ( isCalculateVolWeight ){
                double KG_VOL = ( vol / route.getVolConversion() ) * 1000000;
                BigDecimal transfer = new BigDecimal(KG_VOL);
                // 保留两位小数
                KG_VOL = transfer.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                if( KG_VOL > finalKG){

                    // 根据大的重量计算进位方式
                    if(route.getAfterWeightCarry() != null && KG_VOL >= route.getAfterWeightCarry()){
                        type = route.getAfterWeightCarryType();
                    }else {
                        type = route.getBeforeWeightCarryType();
                    }

                    if(!isPin){
                        // 重新计算价格区间
                        routePrice = getCurrentRouteByKg(KG_VOL,priceList);

                        if( priceList.get(priceList.size() - 1).getEndWeight() < KG_VOL){
                            System.out.println("体积重量超出最大限度，返回");
                            return null;
                        }

                    }


                    KG_VOL = weightCarry(routePrice.getStartWeight(), type, KG_VOL); // 得到最终进位的KG

                    if( routePrice.getStartWeight().compareTo(routePrice.getEndWeight()) == 0){
                        // 单位价格对应总运费
                        price = routePrice.getPrice();
                    }else {
                        price = BigDecimal.valueOf(KG_VOL).multiply(routePrice.getPrice());
                    }

                    System.out.println("==============重新计算的最终重量：" + KG_VOL + "价格："+ price);
                    // 重量体积标识符
                    resultMap.put("KG_VOL", KG_VOL);
                }
            }

            /**
             * 大于限重，则需要叉车费
             */
            if(route.getGreaterWeight() != null && kg >= route.getGreaterWeight()){
                System.out.println("==============添加叉车费之前：" + price);
//                price = price.add(route.getForkliftFee());
                System.out.println("==============添加叉车费之后：" + price);
                resultMap.put("ForkliftFee", route.getForkliftFee()); // 叉车费标识符
            }
            // 返回
            resultMap.put("finalKG", finalKG);
            resultMap.put("finalPrice", price.setScale(2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("route", routePrice.getId());
        }
        return resultMap;
    }

    /**
     * 计算进位规则后的重量
     * @param startKg 开始区间的重量
     * @param type 1、大于0进0.5   2、大于0进1  3、不进位
     * @param kg 需要计算的重量
     * @return
     */
    public double weightCarry(double startKg , int type , double kg){
        if( kg <= startKg ){
            return startKg;
        }
        if( 1 == type ){
            return Math.round(kg)-kg> 0 ? Math.ceil(kg) : ( kg - Math.round( kg ) < 0.1 ? (int) kg : (int) kg + 0.5);
        }
        if( 2 == type ){
            kg = Math.ceil(kg);
        }
        if( 3 == type ){
            kg = kg; // 可以不做处理
        }
        return kg;
    }
    /**
     *  根据选定的路线计算m³的价格，这条渠道价钱是m³计算的，route_type=2
     * @param route 路线
     * @param vol  需要计算的体积
     * @param kg  需要计算的kg
     * @return
     */
    public Map<String,Object> countVOLMoney(List<RoutePrice> priceList, TransportationRoute route, double vol , double kg, boolean isPin){
        Map<String,Object> resultMap = new HashMap<>();
        // 1、 空运  2、海运  后续做路线中取routeType的值
        if(priceList == null || priceList.isEmpty()){
            priceList = routePriceMapper.findByRouteId(route.getId(), 2);
        }

        RoutePrice routePrice = null;
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(priceList)){
            // 如果是拼团计算调用，则不用计算价格区间,
            if(!isPin) {
                // 计算价格区间
                routePrice = getCurrentRouteByVol(vol, priceList);

                if( priceList.get(priceList.size() - 1).getEndVolume() < vol){
                    System.out.println("体积超出最大限制");
                    return null;
                }

            }else {
                routePrice = priceList.get(0);
            }


            double finalVOL = vol;
            // 如果体积小于最小值，则按照最小值计算价格
            if( routePrice.getStartVolume() > vol ){
                finalVOL = routePrice.getStartVolume();
            }

            BigDecimal price = new BigDecimal(0);
            price = BigDecimal.valueOf(finalVOL).multiply(routePrice.getPrice());
            // 如果初始体积等于结尾体积，价格则对应为总运费
            if ( routePrice.getStartVolume().compareTo(routePrice.getEndVolume()) == 0 ){
                price = routePrice.getPrice();
            }


            if(!upperLimitOfWeight(route.getCubeUpper(),kg)){
                // 计算体积重
                double VOL_KG = kg / route.getCubeUpper();
                // 结算体积小于体积重的话，按照体积重算
                if (finalVOL < VOL_KG) {
                    BigDecimal transfer = new BigDecimal(VOL_KG);
                    // 保留四位小数
                    VOL_KG = transfer.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

                    if(!isPin){
                        // 重新计算路线
                        routePrice = getCurrentRouteByVol(VOL_KG,priceList);
                        if( priceList.get(priceList.size() - 1).getEndVolume() < VOL_KG){
                            System.out.println("重量体积超出最大限制");
                            return null;
                        }
                    }


                    // 如果起始区间值相同，则取运费的时候就是总运费，不用相乘
                    if( routePrice.getStartVolume().compareTo(routePrice.getEndVolume()) == 0){
                        price = routePrice.getPrice();
                    }else {
                        price = BigDecimal.valueOf(VOL_KG).multiply(routePrice.getPrice());
                    }

                    System.out.println("==============重新计算的最终价格：" + price);
                    resultMap.put("KG_VOL", VOL_KG); // 体积重标识符
                }
            }

            /**
             * 大于限重，则需要叉车费
             */
            if(route.getGreaterWeight() != null && kg >= route.getGreaterWeight()){
                System.out.println("==============添加叉车费之前：" + price);
//                price = price.add(route.getForkliftFee());
                resultMap.put("ForkliftFee", route.getForkliftFee()); // 叉车费标识符
                System.out.println("==============添加叉车费之后：" + price);
            }
            // 返回
            resultMap.put("finalVOL", finalVOL);
            resultMap.put("finalPrice", price.setScale(2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("route", routePrice.getId());
        }
        return resultMap;
    }

    /**
     * 获取正确的路线区间，从而得到对应价格，计算KG的方法
     * @param kg
     * @param priceList
     * @return
     */
    private RoutePrice getCurrentRouteByKg(double kg, List<RoutePrice> priceList){
        RoutePrice route = null;
        for (int i = 0; i < priceList.size(); i++) {
            if (priceList.get(i).getStartWeight() >= kg) {
                if (i > 0 && priceList.get(i - 1).getEndWeight() >= kg) {
                    route = priceList.get(i - 1);
                } else {
                    route = priceList.get(i);
                }
                break;
            }

//            if(priceList.get(i).getEndWeight() >= kg){
//                route = priceList.get(i);
//                break;
//            }
        }
        /**
         * route == null 这种情况是：KG超出最大的区间的时候，取最近的那个价格
         * 例如 ： 最大的区间是[100,200]，但传进来的KG是400KG，则默认取这个最大的区间
         */
        if (route == null) {
            route = priceList.get(priceList.size() - 1);
        }
        return route;
    }

    /**
     * 获取正确的路线区间，从而得到对应价格，计算vol的方法
     * @param vol
     * @param priceList
     * @return
     */
    private RoutePrice getCurrentRouteByVol(double vol, List<RoutePrice> priceList){
        RoutePrice route = null;
        for (int i = 0; i < priceList.size(); i++) {
            if (priceList.get(i).getStartVolume() >= vol) {
                if (i > 0 && priceList.get(i - 1).getEndVolume() >= vol) {
                    route = priceList.get(i - 1);
                } else {
                    route = priceList.get(i);
                }
                break;
            }
        }
        /**
         * route == null 这种情况是：m³超出最大的区间的时候，取最近的那个价格
         * 例如 ： 最大的区间是[2,5]，但传进来的m³是6m³，则默认取这个最大的区间
         */
        if (route == null) {
            route = priceList.get(priceList.size() - 1);
        }

        return route;
    }

    /**
     * 计算体积共用方法：取4位小数点
     * @param length 长
     * @param width 宽
     * @param height 高
     * author guoshengwen  2019年12月4日11:09:04
     * @return 返回体积
     */
    public static double countVol(double length,double width,double height){
        double vol = (length / 100) * (width / 100) * (height / 100);
        BigDecimal b = new BigDecimal(vol);
        double d = b.setScale(4, BigDecimal.ROUND_UP).doubleValue();
        return d;
    }


}
