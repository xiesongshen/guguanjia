var vm = new Vue({
    el: '.page-content',
    data: {
        qualification: {}
    },
    methods: {
        doUpdate: function (check) {
            this.qualification.check = check;
            this.qualification.address = null;

            axios({
                url: 'manager/qualification/doUpdate',
                method: 'put',
                data: this.qualification
            }).then((response) => {
                console.log(response);
                //注意：parent 是 JS 自带的全局对象，可用于操作父页面
                let index = parent.layer.getFrameIndex(window.name);//获取窗口索引

                if (response.data.success) {
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    parent.layer.success = response.data.success;
                } else {
                    parent.layer.msg(response.data.msg)
                }
            }).catch((error) => {
                parent.layer.msg(error.msg)
            })

        }
    },
    created: function () {
        this.qualification = parent.layer.obj;
    }

})