
/*Дефолтный фильтр*/
var filtering={
    categories: [],
    priceMin: 0,
    priceMax: 0,
    rateMin : 0,
    rateMax : 0,
    soldMin : 0,
    soldMax : 0,
    dateFrom : '',
    dateTo : ''
};

/*Весь фронт маунтится сюда*/
new Vue({
    el:'#app',
    template:`
        <div class="main">
                 <topbar></topbar>
                 <breadcrumbs/>
                 <div class="content-container">
                    <router-view></router-view>
                </div>              
        </div>
   `,
    router,
    components: {
        'sidebar': httpVueLoader('/components/sidebar.vue'),
        'topbar': httpVueLoader('/components/topbar.vue'),
        'breadcrumbs': httpVueLoader('/components/breadcrumbs.vue')
    }
});