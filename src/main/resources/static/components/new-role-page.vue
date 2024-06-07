
<template>
  <div class="container-xxl">
    <h2>Create Form</h2>
    <form id="createForm">
      <div class="form-group">
        <label for="id">ID</label>
        <input type="text" class="form-control" id="id" name="id" required>
      </div>
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" name="name" required>
      </div>
      <div class="form-group">
        <label for="sortOrder">Sort Order</label>
        <input type="number" class="form-control" id="sortOrder" name="sortOrder" required>
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
      </div>
      <div class="form-group">
        <label for="parentId">Parent ID</label>
        <input type="text" class="form-control" id="parentId" name="parentId">
      </div>
      <button type="submit" class="btn btn-primary" v-on:click="handleCreate">Create</button>
    </form>
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      items: [],
    }
  },
  async created(){
    this.items =  await this.getAllRoles();
  },
  methods: {
    handleCreate() {
      // Your login credentials
      var login = 'spring';
      var password = 'secret';

      // Combine the login and password with a colon and then encode it in base64
      var credentials = login + ':' + password;
      var base64Credentials = btoa(credentials);

      // Create the authorization header
      var authorizationHeader = 'Basic ' + base64Credentials;
      document.getElementById('createForm').addEventListener('submit', function (event) {
        event.preventDefault();

        // Get the form data
        var formData = new FormData(event.target);

        // Send the POST request to the server
        fetch('http://localhost:8081/role', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': authorizationHeader
          },
          body: JSON.stringify(Object.fromEntries(formData))
        })
            .then(response => {
              if (response.ok) {
                alert('Form data created successfully.');
                // Reset the form
                event.target.reset();
              } else {
                throw new Error('Failed to create form data.');
              }
            })
            .catch(error => {
              console.error('Error:', error);
              alert('An error occurred while creating the form data.');
            });
      });
    }
  }
};
</script>
<style>
.table-container {
  width: 90vw;
  overflow-x: auto;
}
.table-container table {
  font-size: 12px; /* Set the minimum font size */
}
/* Table with horizontal scroll */
.table-container {
  max-width: 100%;
  overflow-x: auto;
  height: 70vh;
}

.table-fixed {
  overflow-y: auto;
}
table thead th:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background: #fff;
}
table tbody th {
  position: sticky;
  left: 0;
  background: white;
  z-index: 1;
}

/* Fixed first column */
.table-fixed thead td:first-child,
.table-fixed tbody td:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background-color: #fff;
  max-width: 250px;
  overflow: hidden;

}

.table-fixed thead tr:first-child {
  position: sticky;
  left: 0;
  z-index: 20;
  background-color: #fff;


}

.table-fixed thead tr td:first-child{
  background-color: #959595;
  color:#fff;
}

.table-fixed thead tr {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 20;

}
</style>
