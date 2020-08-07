var vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            nodes: [],
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'
                    }
                },
                callback: {
                    onClick: this._onClick,
                },
            }
        }
    },
    methods: {
        initTree: function () {
            axios({
                url: 'manager/office/select'
            }).then(response => {
                this.nodes = response.data.obj;

                this.nodes[this.nodes.length] = {id: 0, name: '归属机构', open: true};
                let zTreeObj = $.fn.zTree.init($('#select-tree'), this.setting, this.nodes);
            }).catch(error => {
                layer.msg(error.message);
            });
        },
        _onClick: function (event, treeId, treeNode) {
            parent.layer.sysOfficeName = treeNode.name;
            parent.layer.officeId = treeNode.id;
            let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        },
    },
    mounted: function () {
        this.initTree();
    },
})