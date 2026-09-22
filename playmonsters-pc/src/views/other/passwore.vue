<template>
    <div class="passwore">
        <TopTitle :title="'密码管理'"></TopTitle>
        <div class="context">
            <div class="left">
                <div class="li">
                    <p>手机号</p>
                    <input type="text" placeholder="请输入手机号" v-model="userArr.phone" disabled>
                </div>
                <div class="li">
                    <p>验证码</p>
                    <div class="inp">
                        <input type="text" placeholder="请输入验证码" v-model="userArr.code">
                        <button :disabled="timer !== 0" @click="getCode">{{ CodeImg }}</button>
                    </div>
                </div>
                <div class="li">
                    <p>设置新密码</p>
                    <input type="password" placeholder="请输入新密码" v-model="userArr.password">
                </div>
                <div class="li">
                    <p>确定新密码</p>
                    <input type="password" placeholder="请确定新密码" v-model="userArr.password2">
                </div>
            </div>
            <div class="right">
                <button @click="changePasswore">确认修改</button>
            </div>
        </div>
        <!-- <div class="bom">
            <div class="content">
                <div class="h5">
                    <h5>密码设置</h5>
                </div>
                <div class="li">
                    <div class="con">
                        <img src="@/assets/icon/icon22.png" alt="">
                        <input type="text" placeholder="请输入手机号" v-model="userArr.phone">
                    </div>
                </div>
                <div class="li">
                    <div class="le">
                        <img src="@/assets/icon/icon23.png" alt="">
                        <input type="text" placeholder="请输入验证码" v-model="userArr.code">
                    </div>
                    <button :disabled="timer !== 0" @click="getCode">{{ CodeImg }}</button>
                </div>
                <div class="li">
                    <div class="con">
                        <img src="@/assets/icon/icon24.png" alt="">
                        <input type="password" placeholder="请输入登陆密码" v-model="userArr.password">
                    </div>
                </div>
                <div class="li">
                    <div class="con">
                        <img src="@/assets/icon/icon24.png" alt="">
                        <input type="password" placeholder="请重复输入新密码" v-model="userArr.password2">
                    </div>
                </div>
                <div class="btn">
                    <button @click="changePasswore">确认修改</button>
                </div>
            </div>
        </div> -->
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref, markRaw } from 'vue';
import { WarningFilled, UserFilled, GoodsFilled, Message } from '@element-plus/icons-vue'
import TopTitle from '@/components/TopTitle.vue'
import kefu from '@/api/kefu';
import { useRouter } from 'vue-router';
export default {
    name: 'passwore',
    components: {
        TopTitle,
        WarningFilled,
        UserFilled,
        GoodsFilled,
        Message
    },
    setup(props, ctx) {
        const route = useRouter();
        const state = reactive({

            disabled: false,    // 获取验证码按钮禁用
            timer: 0,     // 倒计时
            CodeImg: '获取验证码',    // 验证码
            userArr: {
                phone: '',
                code: '',
                password: '',
                password2: ''
            },
            yzmRankey: ''   // 验证码key
        })
        // 获取验证码
        const getCode = () => {
            if (!state.userArr.phone || state.userArr.phone.length != 11) {
                ElMessageBox.confirm(
                    '手机号填写不正确',
                    '错误',
                    {
                        showCancelButton: false,
                        'confirm-button-text': '确定',
                        type: 'warning',
                        icon: markRaw(WarningFilled),
                    }
                )
                return
            } else {
                state.timer = 60
                changeTimer()
            }
            var obj = {
                phone: state.userArr.phone,
                type: 'mima',
            }
            kefu.sendSms(obj).then(res => {
                if (res.code == 200) {
                    ElMessage({
                        message: '验证码发送成功',
                        type: 'success',
                    })
                    //console.log(res.data);
                    state.yzmRankey = res.data.yzmRankey
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'error',
                    })
                }
            })
        }

        // 倒计时
        const changeTimer = () => {
            if (state.timer > 0) {
                state.timer -= 1
                setTimeout(() => {
                    state.CodeImg = `${state.timer} 秒后重新发送`
                    changeTimer()
                }, 1000)
            } else {
                state.CodeImg = '获取验证码'
            }

        }


        // 修改密码
        const changePasswore = () => {
            if (state.userArr.phone == '') {
                alert('请输入手机号');
                return;
            }
            if (state.userArr.code == '') {
                alert('请输入验证码');
                return;
            }
            if (state.userArr.password == '') {
                alert('请输入密码');
                return;
            }
            if (state.userArr.password2 == '') {
                alert('请再次输入密码');
                return;
            }
            if (state.userArr.password != state.userArr.password2) {
                alert('两次密码不一致');
                return;
            }
            //console.log(state.userArr);
            kefu.changePasswore({
                phonenumber: state.userArr.phone, // 手机号
                dxyzm: state.userArr.code,   // 验证码
                password: state.userArr.password2,   // 密码
                yzmRankey: state.yzmRankey,    // 验证码key
            }).then(res => {
                // //console.log(res);
                if (res.code == 200) {
                    alert('修改成功');
                    route.push('/login');
                } else {
                    alert(res.msg);
                }
            })
        }

        onMounted(() => {
            // 获取本地存储的用户信息
            let user = JSON.parse(localStorage.getItem('userInfo'));
            if (user) {
                state.userArr.phone = user.phonenumber;
            }
            //console.log(user);
        })

        return {
            ...toRefs(state),   // 将reactive转换成普通对象
            getCode,    // 获取验证码
            changePasswore, // 修改密码
        }
    }
}
</script>

<style lang="less">
.passwore {
    width: 100%;
    // background-color: #fff;
    background-color: #211c38;
    border: 1px solid #6f6d6d;
    min-height: 700px;


    // .bom {
    //     .content {
    //         display: flex;
    //         align-items: center;
    //         flex-direction: column;
    //         padding-top: 50px;

    //         .li {
    //             display: flex;
    //             align-items: center;
    //             width: 390px;
    //             height: 48px;
    //             margin-bottom: 20px;

    //             .con {
    //                 flex: 1;
    //                 display: flex;
    //                 align-items: center;
    //                 background-color: #eee;
    //                 height: 100%;
    //                 border-radius: 5px;

    //             }

    //             img {
    //                 width: 24px;
    //                 height: 24px;
    //                 margin-left: 10px;
    //                 margin-right: 10px;
    //             }

    //             input {
    //                 flex: 1;
    //                 height: 96%;
    //                 font-size: 14px;
    //                 color: #000;
    //                 border: none;
    //                 background-color: #eee;
    //                 outline: none;
    //             }

    //             .le {
    //                 flex: 1;
    //                 display: flex;
    //                 align-items: center;
    //                 background-color: #eee;
    //                 height: 100%;
    //                 border-radius: 5px;
    //             }

    //             >button {
    //                 height: 48px;
    //                 padding: 0 15px;
    //                 font-size: 14px;
    //                 color: #fff;
    //                 line-height: 48px;
    //                 background-color: #487cf7;
    //                 border-radius: 5px;
    //                 margin-left: 5px;
    //                 border: none;
    //                 cursor: pointer;

    //             }

    //         }

    //         .btn {
    //             button {
    //                 width: 390px;
    //                 height: 48px;
    //                 background: linear-gradient(90deg, #FF8E00, #FF8E00);
    //                 border-radius: 5px;
    //                 font-size: 16px;
    //                 color: #fff;
    //                 line-height: 48px;
    //                 text-align: center;
    //                 margin-top: 50px;
    //                 border: none;
    //                 cursor: pointer;

    //             }
    //         }
    //     }
    // }



    .context {
        display: flex;
        align-items: flex-end;
        justify-content: space-between;
        padding: 10px 30px;
        box-sizing: border-box;

        .left {
            .li {
                p {
                    font-size: 17px;
                    font-weight: 500;
                    color: #fff;
                }

                input {
                    width: 500px;
                    height: 50px;
                    padding: 0 20px;
                    box-sizing: border-box;
                    border-radius: 5px;
                    border-color: #000;
                }

                // 设置input输入框焦点状态的样式
                input:focus {
                    outline: none;
                    border: 1px solid #ee3e23;
                }
            }

            .li:nth-of-type(1) {
                input {
                    // 鼠标移入
                    cursor: not-allowed;
                    color: #fff;
                }
            }

            .li:nth-of-type(2) {
                .inp {
                    display: flex;
                    align-items: center;

                    input {
                        width: 355px;
                        border-top-right-radius: 0;
                        border-bottom-right-radius: 0;
                        border-right: none;
                    }

                    button {
                        width: 150px;
                        height: 50px;
                        line-height: 50px;
                        background: #ee3e23;
                        border-radius: 8px;
                        margin-left: -5px;
                        color: #fff;
                        cursor: pointer;
                        border: none;
                        border-top-left-radius: 0;
                        border-bottom-left-radius: 0;
                    }
                }
            }
        }

        .right {
            button {
                width: 250px;
                height: 50px;
                line-height: 50px;
                background: #ee3e23;
                border-radius: 8px;
                color: #fff;
                cursor: pointer;
                border: none;
            }
        }
    }
}
</style>