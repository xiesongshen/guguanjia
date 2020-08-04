let vm = new Vue({
    el: '.main-content',
    data: {demand: {},
    },
    methods: {},
    created: function () {
        this.demand = parent.layer.obj;
    }
})