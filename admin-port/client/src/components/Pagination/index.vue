/**
 * 页码组件
 * @return {type} {description}
 */

<template>
    <div :class="{'hidden':hidden}"
         class="pagination-container">
        <el-pagination :background="background"
                       :current-page.sync="currentPage"
                       :page-size.sync="pageSize"
                       :layout="layout"
                       :page-sizes="pageSizes"
                       :total="total"
                       v-bind="$attrs"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange" />
    </div>
</template>

<script>
import { scrollTo } from '@/utils/scroll-to'

export default {
    name: 'Pagination',
    props: {
        total: {
            required: true,
            type: Number
        },
        current: {
            type: Number,
            default: 1
        },
        limit: {
            type: Number,
            default: 10
        },
        pageSizes: {
            type: Array,
            default () {
                return [10, 20, 30, 50, 100, 500]
            }
        },
        layout: {
            type: String,
            default: 'total, sizes, prev, pager, next, jumper'
        },
        background: {
            type: Boolean,
            default: true
        },
        autoScroll: {
            type: Boolean,
            default: true
        },
        hidden: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        currentPage: {
            get () {
                return this.current
            },
            set (val) {
                this.$emit('update:page', val)
            }
        },
        pageSize: {
            get () {
                return this.limit
            },
            set (val) {
                this.$emit('update:limit', val)
            }
        }
    },
    methods: {
        handleSizeChange (val) {
            this.$emit('pagination', { page: this.currentPage, limit: val, bool: false })
            if (this.autoScroll) {
                scrollTo(0, 800)
            }
        },
        handleCurrentChange (val) {
            this.$emit('pagination', { page: val, limit: this.pageSize, bool: true })
            if (this.autoScroll) {
                scrollTo(0, 800)
            }
        }
    }
}
</script>

<style scoped>
.pagination-container {
    background: #fff;
    padding: 0 0 10px 0;
}
.pagination-container.hidden {
    display: none;
}
</style>
