<template>
    <div class="quillEditor">
        <quill-editor :value="value"
                      :options="editorOption"
                      @input="getEditorValue($event)">
        </quill-editor>
    </div>
</template>

<script>
import quillConfig from '@/utils/quill-config.js'
export default {
    props: {
        value: {
            type: String,
            required: false
        }
    },
    data () {
        return {
            editorOption: quillConfig
        }
    },
    methods: {
        getEditorValue (val) {
            this.$emit('input', val)
        }
    }
}
</script>

<style lang="scss" scope>
.quill-editor {
    .ql-container {
        height: 170px;
    }
}
.ql-snow .ql-picker {
    height: 23px;
}
</style>