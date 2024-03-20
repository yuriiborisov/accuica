<!--Сайдбар-->
<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Accuica</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item active">
          <router-link to="/privileges">
            <a class="nav-link" href="#">Privileges <span class="sr-only">(current)</span></a>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/roles">
          <a class="nav-link" href="#">Roles</a>
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
module.exports = {
  // computed:{
  //   data(){
  //     return Array.of({
  //       id: "tab1",
  //       name: "Tab",
  //       component: this.roles
  //     });
  //   }
  // },
  // computed:{
  //   data() {
  //     return {
  //       id: "tab1",
  //       name: "Tab",
  //       component: this.roles
  //     }]
  //   },
  // },
  components: {
    'user-info': httpVueLoader('/components/user-info.vue'),
    'tabs': httpVueLoader('/components/tabs.vue'),
    'roles': httpVueLoader('/components/roles.vue'),
    'jsonViewer': httpVueLoader('/components/json-viewer.vue')
  }

};
</script>
<style>
.navbar {
  margin-bottom: 15px;
}

.sidebar a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 18px;
  color: #333;
  display: block;
}

.sidebar a:hover {
  color: #16181b;
}

</style>

