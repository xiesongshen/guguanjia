var vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5,
        },
        type: '',
        flag:true,
        statute:{},
        ueditorConfig:{
            //前端默认ueditor资源文件加载路径
            //VueUeditorWrap会从默认加载路径中加载ueditor.all.min.js和config.js等资源
            UEDITOR_HOME_URL:'static/ueditor/',
            serverUrl:'doUeditor',
            maximumWords:100000
        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/statute/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                params: {type: this.type}
            }).then(response => {
                this.pageInfo = response.data.obj;

            })
        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },
        selectAll: function () {
            this.type = '';
            this._selectPage(1, this.pageInfo.pageSize);
        },
        toUpdate: function (statute) {
            layer.obj = statute;

            layer.open({
                type: 2,
                area: ['50%', '70%'],
                fixed: false, //不固定
                content: 'manager/statute/toUpdate',
                end: () => {
                    /*console.log(layer.success);*/
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            });
        },
        del: function (id) {
            layer.msg('确认删除？', {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: index => {
                    console.log(id);
                    let app = {id: id, delFlag: 1};
                    axios({
                        url: 'manager/statute/doUpdate',
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
        changeActive:function () {
            this.flag = !this.flag;
        },
        save:function () {
            axios({
                url:'manager/statute/save',
                data:this.statute,
                method: 'put'
            }).then(response=>{
                if (response.data.success){
                    layer.msg(response.data.msg)
                    this.changeActive();//修改显示状态
                    //将对象保存后，需要初始化对象
                    this.statute={};
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
    },
    mounted:function () {
        jeDate({
            dateCell: '#indate',   //设置日期插件存放的dom节点
            format: 'YYYY-MM-DD',       //设置日期显示格式
            zIndex: 999999999,           //设置图层
            choosefun:(val)=> {//选中日期后回调
                //动态获取jeDate赋值后的日期，给vue的statute对象的pubDate赋值
                // console.log(val)
                this.statute.pubDate=val;
            }
        });
    },
    components:{//局部注册
        VueUeditorWrap
    }
})