let vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            //初始化  第一次页面加载的时候使用
            pageNum: 1,
            pageSize: 5

        }, //响应结果
    },
    methods: {
        selectPage: function () {

            axios({
                url: `manager/demand/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
            }).then((response) => {
                /*console.log(response);*/
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
        details:function (demand) {
            layer.obj = demand;

            layer.open({
                type: 2,
                area: ['700px', '450px'],
                fixed: false, //不固定
                content: 'manager/demand/toDetails',
            })
            
        },
        toUpdate:function (demand) {
            layer.obj = demand;

            layer.open({
                type: 2,
                area: ['700px', '600px'],
                fixed: false, //不固定
                content: 'manager/demand/toUpdate',
                end: () => {
                    /*console.log(layer.success);*/
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            })
        }

    },
    created:function () {
        this.selectPage();
    }
})