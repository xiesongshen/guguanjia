let vm = new Vue({
    el: '#main-container',
    data: {
        area: {},
        success: ""
    },
    methods: {
        doUpdate: function () {
            axios({
                url: 'manager/area/doUpdate',
                data: this.area,
                method: 'put'
            }).then((response) => {
                if (response.data.success) {
                    //注意：parent 是 JS 自带的全局对象，可用于操作父页面
                    let index = parent.layer.getFrameIndex(window.name);//获取窗口索引

                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    parent.layer.success = response.data.success;
                    this.success = response.data.success;
                } else {
                    parent.layer.msg(response.data.msg)
                }
            }).catch((error) => {
                parent.layer.msg(error.msg)
            })
        },
        closeDo: function () {
            console.log(this.success);
            let index = parent.layer.getFrameIndex(window.name);//获取窗口索引
            if (this.success == undefined || !layer.success) {
                parent.layer.close(index);
            }
        },
        toSelect:function () {
            layer.open({
                type: 2,
                area: ['80%', '80%'],
                content: 'manager/area/toSelect',
                end:()=>{
                    if(layer.parentName!=undefined&&layer.parentName!=''){
                        this.area.parentName=layer.parentName;  //获取从子窗口select传递的parentName
                        this.area.parentId=layer.parentId;
                        this.area.parentIds=layer.parentIds;
                    }
                }
            });
        },
        toModule:function () {
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '80%'],
                content: 'manager/area/toModule',
                end:()=>{
                    if(layer.icon!=undefined&&layer.icon!=''){
                        this.area.icon=layer.icon;  //获取从子窗口modules传递的icon
                    }
                }
            });
        }

    },
    created: function () {
        this.area = parent.layer.obj;

        this.area.oldParentIds=this.area.parentIds;
    }
})