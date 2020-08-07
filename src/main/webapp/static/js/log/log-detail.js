let vm = new Vue({
    el: '#main-container',
    data: {
        log: {}
    },
    methods: {

        closeDo: function () {
            console.log(this.success);
            let index = parent.layer.getFrameIndex(window.name);//获取窗口索引

            parent.layer.close(index);
        }

    },
    created: function () {
        this.log = parent.layer.obj;
    }
})