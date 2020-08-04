var vm = new Vue({
    el: '.page-content',
    data: function () {
        return {
            role: {},
            yxUsers: [], //已选人员列表
            nodes:[],
            setting: {//树配置对象  用于设置树结构全局配置
                data: {
                    simpleData: {//简单数据格式设置
                        enable: true,   //开启简单数据格式   自动将一维数组组装成父子结构
                        pIdKey: 'parentId'  //默认的父id属性名为pId
                    }
                },
                callback: {//回调函数设置，用于设置一些事件回调函数
                    onClick: this._onClick
                },
                view: {//显示回调，当节点显示的时候，会触发回调
                    fontCss: this._fontCss
                }
            },
            oid: '',//公司id
            dxUsers: [],   //待选人员列表
            showDelete: false,
            showInsert: false,
            dxIds: [],      //待选人员中需要添加角色的id数组
            yxIds: [],     //已选人员框中选中的需要移除的人员id
        }
    },
    methods: {
        initTree: function () {
            axios({
                url: 'manager/office/select'
            }).then(response => {
                this.nodes = response.data.obj;

                let zTreeObj = $.fn.zTree.init($('#treeOffice'), this.setting, this.nodes);
            }).catch(error => {
                layer.msg(error.message);
            });
        },
        checkDxUsers: function (id) {//用于绑定组件点击事件，对待选人员处理
            //点击组件后，修改选中布尔值show
            for (let i in this.dxUsers) {
                if(this.dxUsers[i].id===id){
                    this.dxUsers[i].show=!this.dxUsers[i].show;
                    //判断是选中，则将添加人员按钮显示
                    if(this.dxUsers[i].show){
                        this.showInsert=true;
                        //将当前用户id放入dxIds
                        this.dxIds.push(this.dxUsers[i].id);
                    }else{//将当前用户id从dxIds中移除
                        //splice(index,num)  从开始index索引位置删除指定num个数元素
                        for (let j in this.dxIds) {
                            if(this.dxIds[j]===id){//找到需要删除的元素索引j
                                this.dxIds.splice(j,1);
                            }
                        }
                    }
                }
            }
            //判断是否有被选中的选项，否则隐藏按钮
            if(this.dxIds.length<=0){
                this.showInsert=false;
            }
        },
        deleteBatch: function () {
            axios({
                url:'manager/role/deleteBatch',
                //数组对象序列化成字符串，放入地址栏的时候会自动添加key[]  ids->ids[]
                //this.yxIds+''->变成字符串，序列化处理的时候js引擎会将数组处理成 ids='2,26' 放入到地址栏
                //等同于 ids=2&ids=26
                params:{rid:this.role.id,ids:this.yxIds+''}
            }).then(response=>{
                layer.msg(response.data.msg);
                if(response.data.success){
                    //如果删除的已选人员是 现在  选中的公司，就刷新待选人员
                    this.selectNoRole();
                    this.showDelete=false;
                    this.yxIds=[];

                    //刷新已选人员
                    this.selectRole();
                    this.showInsert=false;
                    this.dxIds=[];
                }
            }).catch(error=>{
                layer.msg(error.message);
            });
        },
        insertBatch: function () {
            axios({
                url:'manager/role/insertBatch',
                //数组对象序列化成字符串，放入地址栏的时候会自动添加key[]  ids->ids[]
                //this.dxIds+''->变成字符串，序列化处理的时候js引擎会将数组处理成 ids='2,26' 放入到地址栏
                //等同于 ids=2&ids=26
                params:{rid:this.role.id,cids:this.dxIds+''}
            }).then(response=>{
                layer.msg(response.data.msg);
                if(response.data.success){
                    //刷新待选人员
                    this.selectNoRole();
                    this.showDelete=false;
                    this.yxIds=[];
                    //刷新已选人员
                    this.selectRole();
                    this.showInsert=false;
                    this.dxIds=[];
                }
            }).catch(error=>{
                layer.msg(error.message);
            });
        },
        selectNoRole: function () {
            axios({
                url: 'manager/sysuser/selectNoRole',
                params: {oid: this.oid, rid: this.role.id}
            }).then(response => {
                this.dxUsers = response.data.obj;
                //添加checkbox选中属性
                for (let i in this.dxUsers) {
                    this.dxUsers[i].show = false;
                }
            }).catch(error => {
                layer.msg(error.message);
            });
        },
        selectRole:function () {//选择已分配角色人员
            //根据角色id查询当前角色已授权人员
            axios({
                url:`manager/sysuser/selectByRid`,   //基于base的绝对路径请求
                params:{rid:this.role.id}
            }).then((response)=>{//剪头函数  自动传递上下文的this
                this.yxUsers = response.data.obj;  //获取当前角色已分配的用户列表
                for (let i in this.yxUsers) {
                    this.yxUsers[i].show=false;//动态生成标记变量用于checkbox不选中
                }
            }).catch(error=>{
                console.log(error);
            })
        },
        checkYxUsers:function(id){//用于绑定组件点击事件，对已选人员处理
            //点击组件后，修改选中布尔值show
            for (let i in this.yxUsers) {
                if(this.yxUsers[i].id===id){
                    this.yxUsers[i].show=!this.yxUsers[i].show;
                    //判断是选中，则将移除人员按钮显示
                    if(this.yxUsers[i].show){//只要有一个选中，则可以显示移除人员按钮
                        this.showDelete=true;
                        //将当前用户id放入yxIds
                        this.yxIds.push(this.yxUsers[i].id);
                    }else{//将当前用户id从yxIds中移除
                        //splice(index,num)  从开始index索引位置删除指定num个数元素
                        for (let j in this.yxIds) {
                            if(this.yxIds[j]===id){//找到需要删除的元素索引j
                                this.yxIds.splice(j,1);
                            }
                        }
                    }
                }
            }
            //判断是否有被选中的选项，否则隐藏按钮
            if(this.yxIds.length<=0){
                this.showDelete=false;
            }
        },
        _onClick: function (event, treeId, treeNode) {
            let zTreeObj = $.fn.zTree.getZTreeObj("treeOffice");  //根据树id获取树对象
            //获取所有树节点，多维结构
            let nodes = zTreeObj.getNodes();
            //转换成一维结构
            nodes = zTreeObj.transformToArray(nodes);

            //还原所有高亮属性为false
            for (let i in nodes) {
                nodes[i].highLight = false;
                zTreeObj.updateNode(nodes[i]);
            }
            treeNode.highLight = true;//设置高亮
            zTreeObj.updateNode(treeNode);//更新节点
            this.oid = treeNode.id;
            this.selectNoRole();//查询出待选人员
        },
        _fontCss: function (treeId, treeNode) {

            return treeNode.highLight ? {color: 'red'} : {color: 'black'}
        }
    },
    mounted: function () {
        this.initTree();
    },
    created: function () {
        this.role.id = parent.layer.roleId;
        this.role.name = parent.layer.roleName;
        this.yxUsers = parent.layer.users;
    }
})