var vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            user: {
                roleId: ''
            },
            u:{},
            flag: true,
        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/sysuser/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                data: this.user,
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
        selectAll: function () {
            this.user = {
                name: '',
                phone: '',
                roleId: ''
            };
            this.pId = '';
            this._selectPage(1, this.pageInfo.pageSize)
        },
        toUpdate: function (user) {
            layer.obj = user;
            layer.open({
                type: 2,
                area: ['80%', '70%'],
                fixed: false, //不固定
                content: 'manager/sysuser/toUpdate',
                end: () => {
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            })
        },
        doDel: function (id) {
            layer.msg('确认删除？', {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: index => {
                    console.log(id);
                    let user = {id: id, delFlag: 1};
                    axios({
                        url: 'manager/sysuser/doUpdate',
                        data: user,
                        method: 'put'
                    }).then((response) => {
                        if (response.data.success) {
                            layer.close(index);
                            layer.msg(response.data.msg);
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
        changeActive: function () {
            this.flag = !this.flag;
        },
        toInsert: function () {
            axios({
                url: 'manager/sysuser/save',
                method: 'post',
                data: this.u
            }).then((response) => {
                if (response.data.success) {
                    layer.msg(response.data.msg)
                    this.changeActive();//修改显示状态
                    //将对象保存后，需要初始化对象
                    this.u = {
                        roleId: ''
                    };
                    this.selectPage();
                } else {
                    layer.msg(response.data.msg)
                }

            }).catch((error) => {
                layer.msg(error.msg)
            })
        }
    },
    created: function () {
        this.selectPage();
    },
})