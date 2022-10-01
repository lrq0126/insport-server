<template>
  <div>
    <!-- 微信图片加载必须标签，配合微信图片链接拼接时间戳一起使用 -->
    <meta name="referrer" content="no-referrer">
    <el-dialog :visible.sync="materialDialog" append-to-body width="80%" :close-on-click-modal="false">
      <el-form inline label-width="100px">
        <el-form-item label="素材类型">
          <el-select v-model="pageInfo.materialType" @change="handleSearch(1)">
            <el-option
              v-for="item in materialTypes"
              :key="item.value"
              :value="item.value"
              :label="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleSearch(1)">查 询</el-button>
        </el-form-item>
      </el-form>

      <div style="display: flex; flex-wrap:wrap">
        <div
          style="margin-bottom: 10px; margin-left: 5%; width: 15%"
          v-for="item in materialData"
          :key="item.media_id"
        >
          <div style="margin-left: 30%; width: 70%">
            <el-image
              v-if="pageInfo.materialType == 'news'"
              style="width: 100px; height: 100px; marginright: 10px"
              :src="item.thumb_url"
              :preview-src-list="[item.thumb_url]"
            />

            <el-image
              v-if="pageInfo.materialType == 'image'"
              style="width: 100px; height: 100px; marginright: 10px"
              :src="item.url"
              :preview-src-list="[item.url]"
            />
          </div>
          <div style="width: 100%; text-align: center">
            {{ item.name }}
          </div>
          <div style="width: 100%; text-align: center">
            <el-button size="mini" @click="submitMaterial(item)"
              >选择素材</el-button
            >
          </div>
        </div>
      </div>

      <div style="width: 100%">
        <pagination
          v-show="pageInfo.total > 0"
          :total="pageInfo.total"
          :page.sync="pageInfo.page"
          :limit.sync="pageInfo.limit"
          @pagination="pagination"
        />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMaterialList } from "@/api/operating-windows/wechat-manage";
import Pagination from "@/components/Pagination";
export default {
  components: {
    Pagination,
  },

  data() {
    return {
      materialDialog: false,
      loading: false,
      materialData: [],
      pageInfo: {
        page: 1,
        total: 0,
        limit: 10,
        materialType: "",
      },

      materialTypes: [
        {
          value: "image",
          name: "图片",
        },
        {
          value: "video",
          name: "视频",
        },
        {
          value: "voice",
          name: "语音 ",
        },
        {
          value: "news",
          name: "图文",
        },
      ],
    };
  },

  methods: {
    /**
     * 分页
     * @param {object}
     * @return {type} {description}
     */
    pagination(pageObj) {
      if (pageObj.bool) {
        this.pageInfo.page = pageObj.page;
        this.handleSearch();
      } else {
        this.pageInfo.limit = pageObj.limit;
        this.handleSearch(1);
      }
    },

    openWeChatMaterial(menuType) {
      this.materialDialog = true;
      let materialType;
      if(menuType == "media_id"){
        materialType = "image"
      }else{
        materialType = "news"
      }
      this.pageInfo.materialType = materialType;
      this.resetPage();
      this.handleSearch(1);
    },

    submitMaterial(data) {
      this.$emit("updateForm", data);
      this.materialDialog = false;
    },

    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }

      this.pageInfo.offset =
        this.pageInfo.page > 0
          ? (this.pageInfo.page - 1) * this.pageInfo.limit
          : 0;
      getMaterialList(this.pageInfo)
        .then((res) => {
          this.loading = true;
          if (res.code == 100) {
            this.materialData = res.content.item;
            this.pageInfo.total = res.content.total_count;
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 800);
        });
    },

    resetPage() {
      this.materialData = [];
      this.pageInfo.page = 1;
      this.pageInfo.total = 0;
    },
  },
};
</script>

<style scoped>
</style>