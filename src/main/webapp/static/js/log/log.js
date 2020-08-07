var vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            log: {},

        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/syslog/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                data: this.log,
                method: 'post'
            }).then((response) => {

                this.pageInfo = response.data.obj;

            }).catch((error) => {
                layer.msg(error.massage);
            })

        },

        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },

        toDetail: function (log) {
            layer.obj = log;
            layer.open({
                type: 2,
                area: ['80%', '80%'],
                fixed: false, //不固定
                content: 'manager/syslog/toDetail',
            });
        },

        doDel: function (id) {
            layer.msg('确认删除？', {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: index => {

                    axios({
                        url: 'manager/syslog/doDel',
                        params: id,
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
        }

    },
    created: function () {
        this.selectPage();
    },
})