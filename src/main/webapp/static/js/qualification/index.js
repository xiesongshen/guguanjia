var vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            //初始化  第一次页面加载的时候使用
            pageNum: 1,
            pageSize: 5

        }, //响应结果
        qua: {
            type: "",
            check: "",
        }
    },
    methods: {
        selectPage: function () {

            axios({
                url: `manager/qualification/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                data: this.qua,
                method: 'post'
            }).then((response) => {
                console.log(response.data.obj);
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

        selectAll: function () {
            this.qua = {
                type: "",
                check: "",
            };
            this._selectPage(1, this.pageInfo.pageSize);
        },
        toUpdate: function (qualification) {
            axios({
                url: `manager/qualification/getPath/${qualification.uploadUserId}`,
            }).then((response) => {
                qualification.address = response.data.obj + "/" + qualification.address;

                layer.obj = qualification;
                layer.open({
                    type: 2,
                    area: ['700px', '600px'],
                    fixed: false, //不固定
                    content: 'manager/qualification/toUpdate',
                    end: () => {
                        this.selectPage();
                    }
                })
            })
        }
    },
    created: function () {
        this.selectPage();
    }
})