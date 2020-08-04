let vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            //初始化  第一次页面加载的时候使用
            pageNum: 1,
            pageSize: 5

        }, //响应结果
        flag:true,
        app:{}
    },
    methods: {
        selectPage: function () {

            axios({
                url: `manager/app/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
            }).then((response) => {
                console.log(response);
                this.pageInfo = response.data.obj;

            }).catch((error) => {
                console.log(error);
            })
        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },

        del: function (id) {
            layer.msg('确认删除？', {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: index => {
                    console.log(id);
                    let app = {id: id,delFlag: 1};
                    axios({
                        url: 'manager/app/doUpdate',
                        data: app,
                        method: 'put'
                    }).then((response) => {
                        if (response.data.success) {
                            layer.close(index);
                            layer.msg(response.data.msg)
                            this.selectPage();
                        } else {
                            layer.msg(response.data.msg)
                        }
                    }).catch((error) => {
                        layer.msg(error.msg)
                    })
                }
            });
        },

        toUpdate: function (app) {
            layer.obj = app;

            layer.open({
                type: 2,
                area: ['700px', '450px'],
                fixed: false, //不固定
                content: 'manager/app/toUpdate',
                end: () => {
                    /*console.log(layer.success);*/
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            });
        },
        changeActive:function () {
            this.flag = !this.flag;
        },
        save:function () {

            axios({
                url:'manager/app/save',
                method: 'post',
                data:this.app
            }).then((response)=>{
                if (response.data.success){
                    layer.msg(response.data.msg)
                    this.changeActive();//修改显示状态
                    //将对象保存后，需要初始化对象
                    this.app={}
                    this.selectPage();
                }else {
                    layer.msg(response.data.msg)
                }

            }).catch((error)=>{
                layer.msg(error.msg)
            })
        }
    },
    created: function () {
        this.selectPage();
    }
})