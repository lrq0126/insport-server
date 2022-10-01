/**
 * 左侧导航栏图标
 * @return {type} {description}
 */

<script>
export default {
    name: 'MenuItem',
    functional: true,
    props: {
        icon: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: ''
        }
    },
    render (h, context) {
        const { icon, title } = context.props
        const vnodes = []

        if (icon) {
            vnodes.push(<img src={icon} style="width: 16px;height: 16px;margin-right: 5px;" />)
            // vnodes.push(<svg-icon icon-class={icon} />)
        }

        if (title) {
            vnodes.push(<span slot='title'>{(title)}</span>)
        }
        return vnodes
    }
}
</script>
