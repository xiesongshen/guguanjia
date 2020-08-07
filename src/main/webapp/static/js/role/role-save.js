var vm = new Vue({
    el:'.main-container',
    data:function () {
        return{
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'
                    }
                },
                check:{//选项框设置
                    enable:true,  //开启选项框
                    chkboxType:{'Y':'ps','N':'ps'}  //Y控制选中   N控制取消选中   p:父项关联  s子项关联
                }
            },
            success:false,
            nodes: [],
            treeObj: {},
            role:{
                offices:null,  //新公司
                oldOffices:null, //旧授权公司
                resources:null, //新权限
                oldResources:null  //旧授权权限
            },
            resources:[],//存放当前角色的权限数组
            officeNodes:[],//公司节点数组
            officeTreeObj:'',//公司树对象
            offices:[]//存放当前角色的授权公司数组
        }
    },
    methods:{
        initTree:function () {
            axios({
                url:'manager/menu/list',
            }).then(response=>{
                this.nodes = response.data.obj;

                this.nodes[this.nodes.length]={
                    "id": 0,
                    "name": "所有权限",
                    "checked":true ,
                    open:true//设置默认选中
                }//动态设置根节点

                //根据当前树的全部节点中查找到需要设置选中的节点，选中处理  (显示已分配权限)
                this.selectByRid();
            }).catch(error=>{
                layer.msg(error.message);
            })
        },
        selectByRid:function () {
            axios({
                url:'manager/menu/selectByRid',
                params:{rid:this.role.id}
            }).then(response=>{
                this.resources = response.data.obj;
                for (let i in this.nodes) {
                    for (let j in this.resources) {
                        if(this.nodes[i].id===this.resources[j].id){//找出需要设置checked属性节点
                            this.nodes[i].checked=true;
                            break;
                        }
                    }
                }
                this.treeObj = $.fn.zTree.init($('#select-treetreeSelectResEdit'),this.setting,this.nodes);
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        initOfficeTree:function () {
            axios({
                url:'manager/office/select',
            }).then(response=>{
                this.officeNodes = response.data.obj;

                this.officeNodes[this.officeNodes.length]={
                    "id": 0,
                    "name": "所有机构",
                    open:true//设置默认选中
                }//动态设置根节点

                //根据角色id查询当前角色的已授权所有公司，并且从treeObj中找到对应的节点，设置选中
                this.selectOfficesByRid();
            }).catch(error=>{
                layer.msg(error.message);
            })
        },
        selectOfficesByRid:function () {
            axios({
                url:'manager/office/selectByRid',
                params:{rid:this.role.id}
            }).then(response=>{
                this.offices = response.data.obj;
                for (let i in this.officeNodes) {
                    for (let j in this.offices) {
                        if(this.officeNodes[i].id===this.offices[j].id){//找出需要设置checked属性节点
                            this.officeNodes[i].checked=true;
                            break;
                        }
                    }
                }
                this.officeTreeObj = $.fn.zTree.init($('#select-treetreeSelectOfficeEdit'),this.setting,this.officeNodes);

                //显示div
                $('#treeSelectOfficeEdit').css('display','inline-block');
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        changeDataScope:function () {
            if(this.role.dataScope==='9'){
                if(this.officeTreeObj!=undefined&&this.officeTreeObj!=''){
                    //1.已经是9改成其他后再改回
                    $('#treeSelectOfficeEdit').css('display','inline-block');
                }else{ //2.由其他改为9
                    this.initOfficeTree();
                }
            }else{
                //如果是9改为其他
                if(this.officeTreeObj!=undefined&&this.officeTreeObj!=''){
                    $('#treeSelectOfficeEdit').css('display','none');
                }
            }
        },
        doUpdate:function () {
            //给旧节点数组赋值
            this.role.oldOffices = this.offices;
            this.role.oldResources = this.resources;
            //获取现在被选中的权限和公司数组
            this.role.resources = this.treeObj.getCheckedNodes(true);
            //去掉动态添加的根节点
            if(this.role.resources!=undefined&&this.role.resources[0].id===0){
                this.role.resources.splice(0,1);//移除一个元素
            }


            this.role.offices =  this.officeTreeObj.getCheckedNodes(true);
            if(this.role.offices!=undefined&&this.role.offices[0].id===0){
                this.role.offices.splice(0,1);
            }

            axios({
                url:'manager/role/doUpdate',
                method:'put',
                data:this.role
            }).then(response => {
                let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.success = response.data.success;
                if(response.data.success){//成功更新，关闭当前子窗口，并且通过父窗口提示
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    this.success = response.data.success;
                    parent.layer.success = response.data.success;

                }else{
                    layer.msg(response.data.msg);//子窗口中提示
                }

            }).catch(function (error) {
                layer.msg(error.message);
            })

        },
        doClose:function () {
            let index = parent.layer.getFrameIndex(window.name);//获取窗口索引
            if (this.success == undefined || !layer.success) {
                parent.layer.close(index);
            }
        },
        toSelect:function () {
            layer.open({
                type: 2,
                area: ['80%', '80%'],
                content: 'manager/role/toSelect',
                end:()=>{
                    if(layer.sysOfficeName!=undefined&&layer.sysOfficeName!=''){
                        this.role.sysOfficeName=layer.sysOfficeName;  //获取从子窗口select传递的parentName
                        this.role.officeId=layer.officeId;
                    }
                }
            });
        },
    },
    created: function () {
        this.role = parent.layer.obj;

    },
    mounted: function () {
        this.initTree();//初始化资源/权限树
        //如果默认值是按明细，初始化公司树
        if(this.role.dataScope==='9'){
            this.initOfficeTree();
        }
    },
})