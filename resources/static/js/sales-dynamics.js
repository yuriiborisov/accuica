/*Динамика продаж*/
Vue.component ('sales-dynamics',{
    template:salesDynamics.template,
});

/*Таблица Динамика продаж*/
Vue.component('wb-sales-dynamics-table',{
    template: salesDynamicsTable.template,

    created:function (){
        this.$http.get('/api/shop/wildberries/sales', {params:{page:this.page,size:this.pageSize}}).then(result =>
            result.json().then(data =>
                this.tableData_ = data))
    },
    data: function(){
        return{
            page: 1,
            pageSize: 20,
            total: 841%10,
            tableData_: [],
            idVendor:'',
            url: '',
            srcList: [],
            productDataList:[],
        }
    },
    methods : {
        setPage (val) {
            this.page = val;
            this.$http.get(SALES_API, {params:{page:this.page,size:this.pageSize}}).then(result =>
                result.json().then(data =>
                    this.tableData_ = data ));
            console.log(this.tableData_);
        },
        productDataJson(vendorid){
            this.$http.get(SALES_API+'/' + vendorid).then(result =>
                result.json().then(data =>
                    this.productDataList = data))
        },
        slist(val){
            this.srcList = val;
            console.log(val);
        },
        handleFunction: function(val)
        {
            console.log("Должен появиться график");
            // this.$refs.popupCard.setVisible();
            // var wbId = val.wildberriesId;
            // this.idVendor = wbId;
            // this.productDataJson(wbId);
            // router.push('/product-card/'+wbId)
        },
    }
});