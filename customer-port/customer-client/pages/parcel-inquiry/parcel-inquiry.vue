<template>
    <view class="parcel-inquiry">
        <form action="search_post.php"
              method="post"
              name="sdh">
            <view class="find">
                <view class="kuai">包裹查询</view>
                <view class="kuai1">为您查询快递运单最新状态哟！</view>
                <view class="bao">
                    <view class="choose">
                        <view class="name">查询类型</view>
                        <view class="select">
                            <view class="bg">
                                <select name="keyword"
                                        class="sele">
                                    <option value="2">国外包裹查询</option>
                                    <option value="1">国内包裹查询</option>
                                </select>
                            </view>
                        </view>
                    </view>
                    <view class="choose">
                        <view class="name">快递单号</view>
                        <view class="select">
                            <input name="prid"
                                   type="text"
                                   placeholder="请输入查询的快递单号"
                                   value=""
                                   class="shu" />
                        </view>
                    </view>
                </view>
                <input type="hidden"
                       name="lan"
                       id="lan"
                       value="" />
                <input type="submit"
                       value=" 查 询 "
                       name="sub"
                       id="sub"
                       class="btn111" />
            </view>
        </form>
        <table border="0"
               cellpadding="0"
               cellspacing="0"
               width="100%">
            <tr>
                <td height="20"></td>
            </tr>
        </table>
    </view>
</template>

<script>
export default {
    data () {
        return {

        };
    }
}
</script>

<style lang="scss">
.parcel-inquiry {
    .find {
        padding: 30upx 24upx 0 24upx;
        .kuai {
            font-size: 44upx;
            color: #313131;
            line-height: 44upx;
            margin-bottom: 20upx;
        }
        .kuai1 {
            font-size: 28upx;
            color: #6c6c6c;
            line-height: 28upx;
            padding-bottom: 40upx;
            border-bottom: 1px solid #eeeeee;
            margin-bottom: 36upx;
        }
        .bao {
            border: 1px solid #d9d9d9;
            border-radius: 10upx;
            padding: 24upx 28upx 0 28upx;
            margin-bottom: 30upx;
            .choose {
                width: 100%;
                height: 60upx;
                border: 1px solid #d9d9d9;
                border-radius: 10upx;
                margin-bottom: 24upx;
                overflow: hidden;
                .name {
                    background: #eeeeee;
                    width: 25.5%;
                    float: left;
                    border-right: 1px solid #d9d9d9;
                    height: 60upx;
                    text-align: center;
                    font-size: 28upx;
                    color: #333333;
                    line-height: 60upx;
                }
                .select {
                    width: 74%;
                    float: left;
                    height: 60upx;
                    display: flex;
                    align-items: center;
                    .bg {
                        background: url(../../static/bg.png) repeat-x;
                        background-size: 100% 60upx;
                        width: 100%;
                        height: 60upx;
                        .sele {
                            background: url(../../static/subscript.png)
                                no-repeat 96%;
                            background-size: 12upx 12upx;
                            width: 100%;
                            height: 60upx;
                            line-height: 60upx;
                            appearance: none;
                            -moz-appearance: none;
                            -webkit-appearance: none;
                            -ms-appearance: none;
                            text-indent: 10upx;
                            cursor: pointer;
                        }
                    }
                }
            }
        }
        input {
            border: 0;
            outline: none;
        }
        .btn111 {
            width: 100%;
            height: 75upx;
            color: #fff;
            font-size: 30upx;
            margin-bottom: 20upx;
            background: #0068ff;
            border-radius: 10upx;
            cursor: pointer;
            text-align: center;
        }
    }
}
</style>
