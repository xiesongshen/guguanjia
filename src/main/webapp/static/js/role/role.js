var vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            role: {
                dataScope: ''
            },
            nodes: [],
            officeName: '全部',
            name: '',
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'
                    }
                },
                callback: {
                    onClick: this._onClick
                },
                view: {
                    fontCss: this._fontCss
                }
            }

        }
    },
    methods: {
        selectPage: function () {

            axios({
                url: `manager/role/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                data: this.role,
                method: 'post'
            }).then((response) => {
                /*console.log(response);*/
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
        toUsers: function (id, name) {
            layer.roleId = id;
            layer.roleName = name;
            //根据角色id查询当前角色已授权人员
            axios({
                url: `manager/sysuser/selectByRid`,
                params: {rid: layer.roleId}
            }).then((response) => {
                layer.users = response.data.obj;
                for (let i in layer.users) {
                    layer.users[i].show = false;
                }
                layer.open({
                    type: 2,
                    title: false,
                    area: ['90%', '90%'],
                    content: 'manager/role/toUser',

                })
            }).catch(error => {
                console.log(error);
            })

        },
        toUpdate: function (r) {
            layer.obj = r;
            layer.open({
                type: 2,
                title: false,
                area: ['90%', '90%'],
                content: 'manager/role/toUpdate',
                end: () => {
                    /*console.log(layer.success);*/
                    if (layer.success == undefined || !layer.success) {
                        this.selectPage();
                    }
                }
            })
        },
        selectAll: function () {
            this.role = {
                dataScope: ''
            };
            this.officeName = '全部';
            this._selectPage(1, this.pageInfo.pageSize);
        },
        initTree: function () {
            axios({
                url: 'manager/office/select'
            }).then(response => {
                this.nodes = response.data.obj;

                this.nodes[this.nodes.length] = {id: 0, name: '全部机构'}

                let zTreeObj = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes);
                console.log(zTreeObj);
            }).catch(error => {
                layer.msg(error.message);
            });
        },
        _onClick: function (event, treeId, treeNode) {
            this.officeName = treeNode.name;

            if (treeNode.id != 0) {
                this.role.officeId = treeNode.id;
            } else {
                this.role.officeId = '';
            }
        },
        search: function () {
            let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");  //根据树id获取树对象
            //getNodesByParamFuzzy(key,value,parentNode) 根据父节点parentNode和指定属性名key查找模糊匹配
            //属性值value的节点
            let fuzzyNodes = zTreeObj.getNodesByParamFuzzy("name", this.name, null);
            //.getNodes()获取所有树节点，多维结构
            let nodes = zTreeObj.getNodes();
            //.transformToArray(nodes)转换成一维结构
            nodes = zTreeObj.transformToArray(nodes);

            //还原所有高亮属性为false
            for (let i in nodes) {
                nodes[i].highLight = false;
                zTreeObj.updateNode(nodes[i]);
            }

            //从树节点中查找对应节点，设置高亮显示属性为true
            for (let i in fuzzyNodes) {

                fuzzyNodes[i].highLight = true;
                zTreeObj.updateNode(fuzzyNodes[i]);
            }
        },
        stop: function (event) {
            if (event.target.id == "pullDownTreeone_1_switch") {
                event.stopPropagation();
            }

        },
        _fontCss: function (treeId, treeNode) {

            return treeNode.highLight ? {color: 'red'} : {color: 'black'}
        }
    },
    created: function () {
        this.selectPage();
    },
    mounted: function () {
        this.initTree();

    },
})