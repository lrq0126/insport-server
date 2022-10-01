package com.example.warehouse.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SysLog {
    /**
     * 操作对象单（例如： 快递单号，发货订单）
     *
     * @return 对象 ID
     */
    String contentId() default "";

    String value() default "";

    Type type() default Type.DEFAULT;

    enum Type {

        /**
         * 默认动作
         */
        DEFAULT(""),
        /**
         * 查询
         */
        SELECT("查询"),
        /**
         * 添加
         */
        ADD("添加"),
        /**
         * 更新
         */
        UPDATE("更新"),
        /**
         * 上架
         */
        PUTON("上架"),
        /**
         * 登录
         */
        LOGIN("登录"),
        /**
         * 删除
         */
        DELETE("删除"),
        /**
         * 停用
         */
        DISABLED("停用"),
        /**
         * 启用
         */
        ENABLED("启用"),
        /**
         * 发布
         */
        PUBLISH("发布"),
        /**
         * 置顶
         */
        UP("置顶");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}
