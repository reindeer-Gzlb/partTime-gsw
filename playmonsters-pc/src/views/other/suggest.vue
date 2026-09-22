<template>
    <div class="suggest">
        <!-- <div class="location">
            <el-breadcrumb :separator-icon="ArrowRight">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/index/accountOrder' }">帮助中心</el-breadcrumb-item>
                <el-breadcrumb-item>投诉建议</el-breadcrumb-item>
            </el-breadcrumb>
        </div> -->
        <TopTitle title="投诉建议">
            <!-- <div class="right">
                <img src="../../assets/fh-icon/tousu_list.png" alt="" @click="goLink('/index/other/ProblemList')">
            </div> -->
        </TopTitle>
        <div class="content">
            <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="auto" class="demo-ruleForm">
                <el-form-item label="反馈类型" prop="type">
                    <el-select v-model="ruleForm.type" class="m-2" placeholder="Select" size="large">
                        <el-option v-for="item in typeOption" :key="item.id" :label="item.name" :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="详情内容" prop="content">
                    <el-input type="textarea" rows="4" v-model="ruleForm.content" placeholder="请输入不低于15个字的详细说明"
                        maxlength="200" show-word-limit>
                        <template #prefix>
                            <el-icon class="el-input__icon">
                                <UserFilled />
                            </el-icon>
                        </template>
                    </el-input>

                </el-form-item>
                <el-form-item label="问题图片" prop="imgurl">
                    <el-upload class="avatar-uploader" :action="uploadUrl" :show-file-list="true"
                        :on-success="handleAvatarSuccess" list-type="picture-card" :file-list="fileList" :limit="5"
                        :on-remove="removeImg" accept=".jpg,.png,.jpeg">
                        <el-icon class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                    <div style="color: #fff;margin-left: 20px;">支持jpg，png格式，最多上传5张</div>
                </el-form-item>
                <el-form-item label="联系电话" prop="tel">
                    <el-input style="width: 250px;" v-model="ruleForm.tel" placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="联系QQ" prop="qq">
                    <el-input style="width: 250px;" v-model="ruleForm.qq" placeholder="请输入联系QQ"></el-input>
                </el-form-item>
                <el-form-item>
                    <div class=" btns">
                        <el-button type="info" class="cancel" @click="cancel">取消</el-button>
                        <el-button style="background-color: #ee3e23;color: #fff;border: none" @click="submitForm">提交</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script lang="ts">
import { reactive, toRefs, onMounted, onUnmounted, ref, markRaw } from 'vue';
import { useRouter } from 'vue-router';
import { Plus, SuccessFilled, WarningFilled } from '@element-plus/icons-vue'
import kefu from "@/api/kefu"
import TopTitle from '@/components/TopTitle.vue'
import config from '@/api/config'
export default {
    name: 'zhinan',
    components: {
        Plus,
        SuccessFilled,
        WarningFilled,
        TopTitle
    },
    setup(props, ctx) {
        const route = useRouter();
        const router = useRouter();
        const state = reactive({
            title: '',
            text: ``,
            uploadUrl:config. apiBaseUrl+'/upload/image',
            fileList: [],
            info: {},
            typeOption: [
                {
                    name: '改进建议',
                    id: 1
                },
                {
                    name: '问题反馈',
                    id: 2
                },
                {
                    name: '投诉举报',
                    id: 3
                },
            ]
        })
        const ruleFormRef = ref()
        const ruleForm = reactive({
            qq: '',
            tel: '',
            imgurl: '',
            createId: '',
            title: '',
            content: '',
            type: 1

        })
        const getInfo = () => {
            let info = window.localStorage.getItem('userInfo')
            if (info) {
                state.info = JSON.parse(info)
            } else {
                route.replace({ path: '/login' })
            }
        }

        const rules = reactive<FormRules>({
            tel: [
                { required: true, message: '请输入手机号', trigger: 'blur' },
                { min: 11, max: 11, message: '请输入11位手机号', trigger: 'blur' },
            ],
            qq: [
                { required: true, message: '请输入QQ', trigger: 'blur' }
            ],
            content: [
                { required: true, message: '请输入反馈内容', trigger: 'blur' }
            ],
        })
        const handleAvatarSuccess = (red, file, fileList) => {
            //console.log("%c Line:45 🌶 fileList", "color:#7f2b82", fileList);
            state.fileList = fileList
        }
        // 详情图片删除
        const removeImg = (response, fileList, file) => {
            // 1.获取将要删除图片的临时路径
            const filePath = response.url;
            // 2.找到图片对应的索引值
            state.fileList.forEach(function (element, key) {
                if (element.url === filePath) {
                    state.fileList.splice(key, 1);
                }
            });
        }
        const submitForm = async (formEl: ruleFormRef | undefined) => {
            if (state.fileList && state.fileList.length) {
                let newArr = []
                state.fileList.forEach(img => {
                    newArr.push(img.response.data.filePath)
                })
                ruleForm.imgurl = newArr.join(',')
            }
            let info = window.localStorage.getItem('userInfo')
            if (info) {
                state.info = JSON.parse(info)
            }
            ruleForm.createId = state.info.userId
            kefu.addProblem(ruleForm)?.then(res => {
                ElMessageBox.confirm(
                    '反馈成功！',
                    '错误',
                    {
                        showCancelButton: false,
                        'confirm-button-text': '确定',
                        type: 'warning',
                        icon: markRaw(SuccessFilled),
                    }
                )
                ruleForm.qq = ''
                ruleForm.tel = ''
                ruleForm.imgurl = ''
                ruleForm.content = ''
                ruleForm.type = 1
                state.fileList = []


            }).catch(err => {
                // 异常
                ElMessageBox.confirm(
                    '网络错误',
                    '错误',
                    {
                        showCancelButton: false,
                        'confirm-button-text': '确定',
                        type: 'warning',
                        icon: markRaw(WarningFilled),
                    }
                )
            })
            // ruleForm
            //console.log("%c Line:104 🍬 ruleForm", "color:#2eafb0", ruleForm);
            //console.log('submit!')
            // await formEl.validate((valid, fields) => {

            //   // if (valid) {

            //   // } else {
            //   //   //console.log('error submit!', fields)
            //   // }
            // })
        }

        onMounted(() => {
            getInfo()

        });
        const cancel = () => {
            ruleForm.qq = ''
            ruleForm.tel = ''
            ruleForm.imgurl = ''
            ruleForm.content = ''
            ruleForm.type = 1
            state.fileList = []
        }

        route.afterEach((to) => {
            // //console.log('to', to)
            // const { id } = to.query
            window.scrollTo(0, 0);
            // if (id && to.name == 'add') {
            //   state.name = '编辑商品'
            // }
        });

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
            cancel,
            rules,
            ruleFormRef,
            ruleForm,
            submitForm,
            handleAvatarSuccess,
            removeImg,
            getInfo,
            goLink
        };
    }
};
</script>

<style lang="less" scoped>
.suggest {
    background-color: #211c38;
    border: 1px solid #6f6d6d;
    .location {
        margin-bottom: 20px;
    }

    .content {
        width: 100%;
        min-height: 540px;
        // background-color: #fff;
        // margin-top: 10px;
        padding: 20px;
        box-sizing: border-box;

        .btns {
            margin-top: 50px;
            width: 100%;
            display: flex;
            justify-content: center;
            .el-button {
                width: 211px;
                height: 40px;
                border-radius: 4px;
            }

            .cancel.el-button {
                background: #E5E5E5;
                color: #666;
                border: 0;
            }

        }
    }
}

:deep(.el-breadcrumb__inner) {
    font-size: 20px !important;
}

:deep(.el-form-item__label) {
  font-size: 14px;
  font-weight: bold;
  color: #fff;
}
</style>