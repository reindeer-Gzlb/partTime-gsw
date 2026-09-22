<template>
    <div class="mycenter">
        <TopTitle title="个人信息" />
        
        <div class="content">
            <ul>
                <!-- <li>
                    <div class="left">
                        身份认证
                    </div>
                    <div class="right" @click="goLink('/index/other/realname')" v-if="userInfo.realStatus == 2">
                        <img src="../../assets//fh-icon//shiming.png" alt="">
                        <p>已认证</p>
                    </div>
                    <div class="right" @click="goLink('/index/other/realname')" style="background-color: #eeeeee; width: 80px; " v-else>
                        <p style=" color: #333;margin: 0 17px;">未认证</p>
                    </div>
                </li> -->
                <li>
                    <div class="left">
                        昵称
                    </div>
                    <div class="right" @click="show">
                        <p>{{ info.nickName }}</p>
                        <img src="../../assets//fh-icon/yjt.png" alt="">
                    </div>
                </li>
                <li>
                    <div class="left">
                        手机号
                    </div>
                    <div class="right">
                        <p>{{ info.phonenumber }}</p>
                    </div>
                </li>
              
            </ul>
        </div>
    </div>
    
    <!-- 官方视频号 -->
    <ElMessageBox :is_show="is_show" :center="true"  @update="handleChildEvent" :text="text">
        <div class="is_show" @click.stop="on">
            <img class="icon_cha" src="../../assets/fh-icon/cha.png" alt="" @click.stop="handleChildEvent(5)">
            <h2>昵称修改</h2>
            <div class="content">
                <input type="text" placeholder="请输入昵称" v-model="info.nickName">
                <div class="btn">
                    <el-button plain @click="handleChildEvent">取消</el-button>
                    <el-button type="primary" @click="submit">确定</el-button>
                </div>
            </div>
        </div>
    </ElMessageBox>
</template>
 
<script>
import { reactive, toRefs, onMounted } from 'vue';
import kefu from '@/api/kefu';
import commodityList from '@/components/commodityList.vue'
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue'

export default {
    name: 'mycenter',
    conponents: {
        TopTitle
    },
    setup() {
        const router = useRouter();
        const state = reactive({
            is_show: false,
            info: {
                avatar: '', // 头像
                phonenumber: "", // 手机号
                nickName: '', // 昵称
                id:"",  // 用户证件号码
            },
            userInfo: {},
        })

        // 本地获取用户信息
        const getInfo = () => {
            let info = window.localStorage.getItem('userInfo')
            if (info) {
                state.info = JSON.parse(info)
                //console.log("%c Line:47 🌶 state.info", "color:#7f2b82", state.info);
            } else {
                router.replace({ path: '/login' })
            }
        }

        onMounted(() => {
            getInfo()
            // 获取本地存储的用户信息
            let info = window.localStorage.getItem('userInfo')



            // let info = window.localStorage.getItem('userInfo')
            if (info) {
                state.userInfo = JSON.parse(info)
                state.info.nickName = state.userInfo.nickName
                // state.info.phonenumber = state.userInfo.phonenumber.replace(/(\d{3})\d{9}(\d{4})/, '$1****$2')
                if(state.info.id) {
                    state.info.id = state.userInfo.idCard.substring(0, 3) + "***************";
                }

                //console.log("%c Line:47 🌶 state.info", "color:#7f2b82", state.userInfo.idCard);
            }
        })

        // 关闭弹窗
        const handleChildEvent = () => {
            state.is_show = false
            // 让浏览器滚动条显示
            document.documentElement.style.overflow = 'auto';
        }

        // 开启弹窗
        const show = () => {
            state.is_show = true
            // 让浏览器滚动条隐藏
            document.documentElement.style.overflow = 'hidden';
        }

        // 修改用户信息
        const submit = () => {
            kefu.updateZeuser(state.info).then(res => {
                ElMessage({
                    type: 'success',
                    message: '修改成功'
                })
                // 对state.info.nickName判断是否为手机号或大于11位的数字，是就私密处理
                if (state.info.nickName.length > 11 || !isNaN(state.info.nickName)) {
                    state.info.nickName = state.info.nickName.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
                    window.localStorage.setItem('userInfo', JSON.stringify(state.info))
                } else {
                    // 本地存储用户信息
                    window.localStorage.setItem('userInfo', JSON.stringify(state.info))
                }
                // 刷新网页
                setTimeout(() => {
                    window.location.reload()
                }, 1000);
            })
        }

        const on = () => {
            return false
        }

        // 跳转
        const goLink = (link, query) => {
            if (query) {
                router.push({ path: link, query: query });
            } else {
                router.push({ path: link });
            }
        };

        return {
            ...toRefs(state),
            handleChildEvent,
            show,
            getInfo,
            submit,
            on,
            goLink
        }
    }
}
</script>

<style lang="less">
.is_show {
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    padding: 0;
    border-radius: 5px;

    .icon_cha {
        width: 25px;
        height: 25px;
        position: absolute;
        top: 20px;
        right: 20px;
        cursor: pointer;
    }

    h2 {
        text-align: center;
        font-size: 18px;
        font-weight: 500;
    }

    .content {
        width: 400px;
        height: 200px;
        padding: 20px 0;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;

        input {
            width: 350px;
            height: 40px;
            padding: 0 20px;
            box-sizing: border-box;
        }

        .btn {
            width: 100px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
    }
}


.mycenter {
    width: 100%;
   
    border: 1px solid #6f6d6d;

    .content {
        width: 100%;
        min-height: 500px;
        // background-color: #fff;
        background-color: #211c38;
        padding: 0 30px;
        box-sizing: border-box;

        ul {
            margin: 0;
            padding: 0;
            box-sizing: border-box;

            // li:nth-of-type(1) {
            //     box-sizing: border-box;
            //     display: flex;
            //     align-items: center;
            //     justify-content: space-between;
            //     height: 75px;
            //     border-bottom: 1px solid #eaeaea;
            //     margin: 0 25px;
            //     margin: 0;

            //     .left {
            //         font-size: 17px;
            //     }

            //     .right {
            //         display: flex;
            //         align-items: center;
            //         width: 92px;
            //         height: 33px;
            //         background: #f7f7f7;
            //         border-radius: 17px;
            //         background: #feeced;
            //         cursor: pointer;
            //         padding: 0 5px;

            //         img {
            //             width: 20px;
            //             height: 20px;
            //         }

            //         p {
            //             margin: 0;
            //             margin-left: 7px;
            //             color: #FF8E00;
            //         }
            //     }
            // }

            li {
                box-sizing: border-box;
                display: flex;
                align-items: center;
                justify-content: space-between;
                height: 75px;
                border-bottom: 1px solid #eaeaea;
                margin: 0 25px;
                margin: 0;
                color: #fff;

                .left {
                    font-size: 17px;
                }

                .right {
                    display: flex;
                    align-items: center;

                    img {
                        width: 8px;
                        height: 14px;
                        margin-left: 12px;
                    }
                }
            }
        }
    }
}

</style>