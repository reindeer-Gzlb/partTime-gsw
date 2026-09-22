<template>
    <div class="TopTitle">
        <span>{{ props.title }}</span>
        <slot></slot>
    </div>
</template>

<script>
export default {
    name: 'TopTitle',
    props: {
        title: {
            type: String,
            default: ''
        }
    },
    setup(props) {
        return {
            props
        }
    }
}
</script>

<style lang="less">
.TopTitle {
    width: 100%;
    height: 67px;
    line-height: 67px;
    background: #1c1632;
    // border: 1px solid #6f6d6d;
    font-size: 18px;
    font-weight: 500;
    padding: 0 25px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: space-between;

    span {
        font-size: 18px;
        color: #fff;
    }
}
</style>