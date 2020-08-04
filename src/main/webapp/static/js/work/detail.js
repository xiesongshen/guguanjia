var vm = new Vue({
    el:'.page-content',
    data:{
        work:{}
    },
    methods:{
        selectDetails:function(id){
            axios({
                url:`manager/admin/selectDetails`,
                params:{id:id}
            }).then((response)=>{
                console.log(response);
                this.work = response.data.obj;

            }).catch((error)=>{
                layer.msg(error);
            })
        }
    },
    created:function () {
        this.selectDetails(parent.layer.obj)
    }
})