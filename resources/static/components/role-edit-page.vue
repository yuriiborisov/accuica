
<template>
  <div class="container-xxl">
    <h2>Create Form</h2>
    <form id="createForm">
      <div class="form-group">
        <label for="id">ID</label>
        <input type="text" class="form-control" id="id" name="id" v-model="data1.id" readonly>
      </div>
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" name="name" :value="data1.name" required>
      </div>
      <div class="form-group">
        <label for="priority">Priority</label>
<!--        <input type="text"  pattern="\d*" class="form-control" id="sortOrder" name="sortOrder" :value="data1.sortOrder" required>-->
        <input type="text" class="form-control" id="priority" name="priority" :value="data1.priority" required>
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea class="form-control" id="description" name="description" rows="3" :value="data1.description" required></textarea>
      </div>
      <div class="form-group">
        <label for="parentId">Parent ID</label>
        <input type="text" class="form-control" id="parentId" name="parentId" :value="data1.parentId" >
      </div>
      <button type="submit" class="btn btn-primary" v-on:click="handleUpdate">Update</button>
    </form>
  </div>
</template>

<script>
module.exports = {
   data(){
    return{
      data1: {}
    }
  },
  async created(){
    this.data1 = await this.getData();
    this.restrictNumberInput();
    console.log('data1',  this.data1)
},
  computed: {
    roleIdFromPath() {
      return this.$route.query.roleId;
    },
  },

  methods: {
    // async getData(){
    //   return await this.getRoleData();;
    // },
    restrictNumberInput(){
      document.querySelector('#priority').addEventListener('input', {
        handleEvent(event) {
          var newValue = event.target.value.replace(new RegExp(/[^\d]/, 'ig'), "");
          event.target.value = newValue;
        }
      });
    },
   async getData() {
     return await fetcher.fetch('/role/' + this.roleIdFromPath,'GET', {
       'Content-Type': 'application/json'
     });
    },
    handleUpdate() {
      document.getElementById('createForm').addEventListener('submit', async function (event) {
        event.preventDefault();

        // Get the form data
        var formData = new FormData(event.target);
        console.log('formData', formData);
        // Send the POST request to the server
        await fetcher.fetch('/role/info', 'PUT',{
              'Content-Type': 'application/json'
            },JSON.stringify(Object.fromEntries(formData))
        );
      });
    }
  }
};
</script>
