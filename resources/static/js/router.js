/*Роутинг по страницам*/
const routes = [
    { path: '/',
        component: {template:`<h2>Home</h2>`},
        meta: {
            breadcrumb: [
                {name: 'Home'},
            ]
        }
    },
    { path: '/privileges',
        component: httpVueLoader('/components/privileges.vue') ,
        meta: {
            breadcrumb: [
                {name: 'Home', link: '/'},
                {name: 'Privileges'}
            ]
        }
    },
    { path: '/roles', component: httpVueLoader('/components/roles.vue'),
        meta: {
            breadcrumb: [
                {name: 'Home', link: '/'},
                {name: 'Roles'}
            ]
        }
    },
    { path: '/matrix', component: httpVueLoader('/components/app/matrix-page.vue'),
        meta: {
            breadcrumb: [
                {name: 'Home', link: '/'},
                {name: 'Roles', link: 'roles'},
                {name: 'Matrix'}
            ]
        }
    },
    { path: '/methods', component: httpVueLoader('/components/methods.vue'),
        meta: {
            breadcrumb: [
                {name: 'Home', link: '/'},
                {name: 'Methods'}
            ]
        }
    },
    { path: '/uica', component: httpVueLoader('/components/uica.vue'),
        meta: {
            breadcrumb: [
                {name: 'Home', link: '/'},
                {name: 'UICA'}
            ]
        }
    },
    { path: '/uicaeditor', component: httpVueLoader('/components/uica-editor.vue'),
        meta: {
            breadcrumb: [
                {name: 'Home', link: '/'},
                {name: 'UICAEditor'}
            ]
        }
    },
    { path: '/tabs', component: httpVueLoader('/components/tabs.vue')},
    { path: '/json-viewer', component: httpVueLoader('/components/json-viewer.vue')},
    // { path: '/new-role', component: httpVueLoader('/components/new-role-page.vue')},
    // { path: '/edit-role', component: httpVueLoader('/components/role-edit-page.vue')},
    // { path: '/sales-dynamics', component: salesDynamics },
    // { path: '/bits-and-pieces', component: bitsAndPieces },
    // { path: '/products/:id', component: httpVueLoader('/components/products/product-card-page.vue') },
    // { path: '/suppliers/:id', component: httpVueLoader('/components/suppliers/supplier-card-page.vue') },
    // // { path: '/price-dynamics', component: priceDynamics },
    // { path: '/favorites', component: httpVueLoader('/components/favoritesProductsPage.vue') },
    // { path: '/suppliers', component: httpVueLoader('/components/suppliers/suppliersPage.vue') },
    // { path: '/brands', component: httpVueLoader('/components/brands/brandsPage.vue') },
    // { path: '/brands/:id', component: httpVueLoader('/components/brands/brand-card-page.vue') },
    // { path: "*", component: httpVueLoader('/components/page-not-found.vue') }
]
/*Роутер*/
const router = new VueRouter({
    routes, // сокращённая запись для `routes: routes`
    mode: "history",
});