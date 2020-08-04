var vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            area: {
                name: ''
            },
            pId: '',
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
                    beforeEditName: this._beforeEditName,
                    beforeRemove: this._beforeRemove,
                },
                edit: {
                    enable: true,
                    removeTitle: '删除',
                    renameTitle: '修改',
                },
                view: {
                    addHoverDom: this._addHoverDom,
                    removeHoverDom: this._removeHoverDom
                }
            }

        }
    },
    methods: {
        selectPage: function () {
            if (this.pId != '') {
                axios({
                    url: `manager/area/selectByPid/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,   //基于base的绝对路径请求
                    params: {id: this.pId}
                }).then((response) => {//剪头函数  自动传递上下文的this
                    this.pageInfo = response.data.obj;
                }).catch(error => {
                    console.log(error);
                })
            } else {
                axios({
                    url: `manager/area/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                    data: this.area,
                    method: 'post'
                }).then((response) => {
                    /*console.log(response);*/
                    this.pageInfo = response.data.obj;

                }).catch((error) => {
                    layer.msg(error.massage);
                })
            }

        },

        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        },
        selectAll: function () {
            this.area = {
                name: ''
            };
            this.pId = '';
            this._selectPage(1, this.pageInfo.pageSize)
        },
        toUpdate: function (area) {
            layer.obj = area;
            layer.open({
                type: 2,
                area: ['80%', '90%'],
                fixed: false, //不固定
                content: 'manager/area/toUpdate',
                end: () => {
                    /*console.log(layer.success);*/
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
                    let area = {id: id, delFlag: 1};
                    axios({
                        url: 'manager/area/doUpdate',
                        data: area,
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
        toInsert: function () {
            layer.open({
                type: 2,
                area: ['80%', '90%'],
                fixed: false, //不固定
                content: 'manager/area/toInsert',
                end: () => {
                    this.initTree();
                    this._selectPage(1, this.pageInfo.pageSize);
                }
            });
        },
        download: function () {
            location.href = "manager/area/download";
        },
        upload: function (event) {
            console.log(event.target.files[0]);
            let formData = new FormData();
            formData.append("file", event.target.files[0])

            axios({
                url: 'manager/area/upload',
                method: 'post',
                headers: {'content-type': 'multipart/form-data'},
                data: formData
            }).then(response => {
                layer.msg(response.data.msg);
                this._selectPage(1,this.pageInfo.pageSize)
            }).catch(error => {
                layer.msg(error.mag);
            })
        },
        initTree: function () {
            axios({
                url: 'manager/area/select'
            }).then(response => {
                this.nodes = response.data.obj;

                this.nodes[this.nodes.length] = {id: 0, name: '区域列表', open: true}

                let zTreeObj = $.fn.zTree.init($('#treeMenu'), this.setting, this.nodes);
            }).catch(error => {
                layer.msg(error.message);
            });
        },
        _onClick: function (event, treeId, treeNode) {
            this.pId = treeNode.id;
            this.selectPage();
        },
        _addHoverDom: function (treeId, treeNode) {
            /*console.log(treeNode);*/
            var tId = treeNode.tId;
            let bigBox = $(`#${tId}_span`);
            let span = `<span class="button add" id="${tId}_add" title="添加" ></span>`;
            let spanObj = $(`#${tId}_add`);
            if (spanObj.length > 0) {
                return
            }
            ;
            bigBox.after(span);

            $(`#${tId}_add`).on('click', this.toInsert);

        },
        _removeHoverDom: function (treeId, treeNode) {

            $(`#${treeNode.tId}_add`).unbind().remove();
        },
        _beforeEditName: function (treeId, treeNode) {
            console.log(treeNode)
            layer.obj = treeNode;
            layer.open({
                type: 2,
                area: ['80%', '90%'],
                fixed: false, //不固定
                content: 'manager/area/toUpdate',
                end: () => {
                    /*console.log(layer.success);*/
                    if (layer.success == undefined || !layer.success) {
                        this.initTree();
                    }
                }
            })

            return false;
        },
        _beforeRemove: function (treeId, treeNode) {
            let id = treeNode.id;
            layer.msg('确认删除？', {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: index => {
                    console.log(id);
                    let area = {id: id, delFlag: 1};
                    axios({
                        url: 'manager/area/doUpdate',
                        data: area,
                        method: 'put'
                    }).then((response) => {
                        if (response.data.success) {
                            layer.close(index);
                            layer.msg(response.data.msg);
                            this.initTree();
                        } else {
                            layer.msg(response.data.msg)
                        }
                    }).catch((error) => {
                        layer.msg(error.msg)
                    })
                }
            });

            return false;
        }

    },
    created: function () {
        this.selectPage();
    },
    mounted: function () {
        this.initTree();

    },
})