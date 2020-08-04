let vm = new Vue({
    el: '.page-content',
    data: {
        demand: {},
        success: ""
    },
    methods: {
        doUpdate: function () {
            axios({
                url: 'manager/demand/doUpdate',
                data: this.demand,
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
        }
    },
    created: function () {
        this.demand = parent.layer.obj;
    }
})