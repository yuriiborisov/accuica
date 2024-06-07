
<template>
  <div>
    <!-- Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="deleteModalLabel"> {{ title }}</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="hideModal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            {{ message }}
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal" @click="hideModal">Cancel</button>
            <button type="button" class="btn btn-danger" @click="handleDelete">Delete</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      title: "",
      message: "",
      action: undefined
    }
  },
  mounted(){
    this.$root.$on("show-delete-modal", (data) => {
      this.showModal()
      this.title = data.title;
      this.message = data.message;
      this.action = data.action;
    });
  },
  updated(){
    if(this.show == true){
      this.showModal();
    }
  },
  created(){
    console.log("tab", this.tabsData);
  },
  methods:{
    showModal() {
      $('#deleteModal').modal('show');
    },
    hideModal() {
      $('#deleteModal').modal('hide');
      this.show = false;
    },
    deleteObject() {
      alert('Object deleted!');
      this.hideModal();
    },
    handleDelete(){
      this.action();
      this.hideModal();
    }
  }
};
</script>
<style>
.nav-tabs {
  margin-bottom: 15px;
}
</style>